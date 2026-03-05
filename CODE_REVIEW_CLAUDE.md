# EasyGym Mobile — Code Review (Claude)

*Revisione basata sull'analisi diretta del codice sorgente del branch `manage-users`.*
*Data: 05/03/2026*

---

## Panoramica

Lo stack tecnologico scelto è moderno e adeguato (Kotlin, Compose, Hilt, Retrofit, Room, DataStore). La struttura a layer `data / domain / ui` è un ottimo punto di partenza. Questa review si concentra sui **problemi concreti trovati nel codice**, ordinati per priorità, con snippet e soluzioni.

---

## CRITICO — Bug e violazioni architetturali che impattano il funzionamento

### 1. Violazione dei layer: `UserRole` definito nell'UI layer

**File:** `ui/navigation/NavViewModel.kt:14`

`UserRole` è un enum definito nel package `ui.navigation`, ma viene importato da:
- `domain/model/User.kt` — il domain model importa dalla UI
- `data/local/entity/UserEntity.kt` — l'entity Room importa dalla UI
- `data/remote/model/user/UserResponse.kt` — il DTO di rete importa dalla UI

Questo inverte completamente la direzione delle dipendenze. I layer `data` e `domain` non devono mai dipendere da `ui`.

**Soluzione:** spostare `UserRole` nel domain layer.

```kotlin
// domain/model/UserRole.kt
package com.easygym.domain.model

enum class UserRole {
    ADMIN, COACH, ATHLETE
}
```

---

### 2. `AuthRepository` (domain) dipende da modelli del layer `data`

**File:** `domain/repository/AuthRepository.kt:5-6`

```kotlin
import com.easygym.data.remote.model.auth.LoginRequest
import com.easygym.data.remote.model.auth.RefreshRequest
```

L'interfaccia `AuthRepository` fa parte del domain layer, ma accetta come parametri classi del layer `data`. Questo rompe la Clean Architecture: il domain non deve conoscere i DTO di rete.

**Soluzione:** definire `LoginCredentials` e `RefreshCredentials` (o simili) nel domain layer e mappare a `LoginRequest`/`RefreshRequest` nell'implementazione `AuthRepositoryImpl`.

---

### 3. Bug di navigazione in `NavGraph`: `startDestination` non è reattivo

**File:** `ui/navigation/NavGraph.kt:47-52`

```kotlin
NavHost(
    navController = navController,
    startDestination = when {
        navState.isLoading -> NavDestination.Common.LOADING.route
        navState.bottomDestinations.isNotEmpty() -> NavDestination.Common.BOTTOM.route
        else -> NavDestination.Common.LOGIN.route
    },
    ...
)
```

`startDestination` in `NavHost` viene valutato **una sola volta** alla prima composizione. Quando `navState` cambia (es. il loading termina), il parametro `startDestination` non causa una rinavigazione automatica: l'utente rimane bloccato sulla schermata di loading.

**Soluzione:** usare `LaunchedEffect` per navigare esplicitamente al cambio di stato.

```kotlin
val startRoute = remember { /* calcola il route iniziale una sola volta */ }

LaunchedEffect(navState.isLoading, navState.bottomDestinations) {
    if (!navState.isLoading) {
        val target = if (navState.bottomDestinations.isNotEmpty())
            NavDestination.Common.BOTTOM.route
        else
            NavDestination.Common.LOGIN.route
        navController.navigate(target) {
            popUpTo(0) { inclusive = true }
        }
    }
}
```

---

### 4. `AthletesViewModel`: race condition e flusso coroutine errato

**File:** `ui/screens/athletes/AthletesViewModel.kt:28-39`

```kotlin
viewModelScope.launch {
    try {
        userRepository.getAll()           // (1) chiamata rete
        _state.update { ... }
    } catch (e: Exception) {
        _state.update { it.copy(errorMessage = e.message) }
    }

    userRepository.users.collect { users ->  // (2) collect dentro il try-catch precedente
        _state.update { ... }
    }
}
```

Problemi:
- Se `getAll()` lancia un'eccezione, l'errore viene catturato ma il `collect` parte ugualmente, con il database ancora vuoto.
- `collect` è una funzione sospesa che non termina mai: il blocco dopo di essa non viene mai eseguito.
- I due blocchi (fetch + observe) sono in sequenza, non separati.

**Soluzione:** separare i due flussi in due coroutine distinte.

```kotlin
init {
    viewModelScope.launch {
        userRepository.users.collect { users ->
            _state.update { it.copy(isLoading = false, users = users) }
        }
    }
    viewModelScope.launch {
        try {
            userRepository.getAll()
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, errorMessage = e.message) }
        }
    }
}
```

---

### 5. `UserEntity`: ID locale invece di ID del server

**File:** `data/local/entity/UserEntity.kt:12`

```kotlin
@PrimaryKey
val id: String = UUID.randomUUID().toString(),
```

L'ID è generato localmente con `UUID.randomUUID()`. Ogni chiamata a `insertAll` con `OnConflictStrategy.REPLACE` crea nuove righe invece di aggiornare quelle esistenti, perché gli ID generati sono sempre diversi. I dati si duplicano ad ogni refresh.

**Soluzione:** usare come `@PrimaryKey` l'ID proveniente dal server (ad esempio `email` se univoca, oppure un `id` che il server deve fornire in `UserResponse`).

---

### 6. Release build mancante di `BASE_URL`

**File:** `app/build.gradle.kts:39-43`

```kotlin
release {
    isMinifyEnabled = false
    proguardFiles(...)
    // BASE_URL non definito!
}
```

Solo il flavor `debug` definisce `BuildConfig.BASE_URL`. Il build di release fallirà in compilazione (o userà un valore vuoto, causando crash a runtime).

**Soluzione:** aggiungere il `buildConfigField` anche al flavor `release`, eventualmente con un URL di produzione diverso da quello di debug.

---

## ALTO — Problemi che degradano la qualità e la manutenibilità

### 7. `runBlocking` negli interceptor OkHttp

**File:** `services/AuthInterceptor.kt:18`, `services/TokenAuthenticator.kt:23`

`runBlocking` in un thread di OkHttp può causare **ANR (Application Not Responding)** se il main thread è coinvolto. Anche se OkHttp usa thread dedicati, il blocco del thread di rete degrada le performance.

Il pattern corretto è usare `runBlocking` con attenzione, oppure — meglio — passare i token come variabili in memoria aggiornate tramite un listener separato (in-memory token cache), leggendo da DataStore solo all'avvio e aggiornando la cache a ogni refresh.

---

### 8. `println` invece di `Log`

**File:** `data/repository/AuthRepositoryImpl.kt:57,65`

```kotlin
println("Login successful: $loginResponse")
println("Refresh successful: $refreshResponse")
```

`println` scrive su stdout e non è visibile in Logcat in modo pulito. Non rispetta i livelli di log e non viene rimosso in produzione dal ProGuard (che comunque è disabilitato, vedi punto 11).

**Soluzione:** usare `Log.d(TAG, "...")` con un tag di classe.

---

### 9. Import `jakarta.inject` invece di `javax.inject`

**File:** `data/repository/UserRepositoryImpl.kt:7`

```kotlin
import jakarta.inject.Inject  // errato
```

Tutti gli altri file usano `javax.inject.Inject`. `jakarta.inject` fa parte delle Jakarta EE APIs e non è lo stesso package usato da Hilt/Dagger su Android. Funziona solo perché entrambi finiscono in classpath, ma è sbagliato semanticamente e può causare problemi in futuro.

---

### 10. `AppModule.kt` completamente commentato

**File:** `di/AppModule.kt`

Il file contiene solo codice commentato. È un residuo di refactoring precedente (il provider del database è stato spostato in `DatabaseModule.kt`). Questo tipo di file va eliminato per evitare confusione.

---

### 11. `isMinifyEnabled = false` in release e `fallbackToDestructiveMigration`

**File:** `app/build.gradle.kts:40`, `di/DatabaseModule.kt:19`

- Il minify disabilitato in release significa che il codice non è offuscato né ottimizzato per la produzione.
- `fallbackToDestructiveMigration()` cancella l'intero database ogni volta che la versione di schema Room aumenta. In produzione questo eliminerebbe i dati degli utenti.

**Soluzione:** abilitare `isMinifyEnabled = true` e definire migrazioni Room esplicite con `addMigrations()`.

---

### 12. Typo nel nome della route

**File:** `ui/navigation/NavDestination.kt:16`

```kotlin
object ATHLETES : BottomBar(route = "atlethes", ...)
//                                    ^^^^^^^^ typo: "atlethes" invece di "athletes"
```

Un typo in una route string non causa errori a compile time ma può causare crash a runtime se la route viene referenziata altrove con la spelling corretta.

---

### 13. `LoadingGateViewModel` è una classe vuota

**File:** `ui/screens/loadinggate/LoadingGateViewModel.kt`

```kotlin
class LoadingGateViewModel
```

Classe senza `@HiltViewModel`, senza `@Inject constructor`, senza nessuna logica. Se non è necessaria, va eliminata. Se serve in futuro, va lasciata con il boilerplate corretto o rimossa e aggiunta quando necessario.

---

### 14. Navigazione basata su stringhe invece di Type-Safe Navigation

**File:** `ui/navigation/NavDestination.kt`, `ui/navigation/NavGraph.kt`

La navigazione tramite stringhe non offre type safety a compile time. Con Navigation Compose 2.8+, è disponibile la navigazione type-safe con classi `@Serializable`:

```kotlin
@Serializable
object LoginRoute

@Serializable
object BottomRoute
```

---

## MEDIO — Best practice e pulizia del codice

### 15. Colori hardcoded nei componenti UI

**File:** `ui/components/PrimaryButton.kt:36`

```kotlin
color = Color(0xFFFFFFFF)  // bianco hardcoded
```

Usare `MaterialTheme.colorScheme.onPrimary` garantisce la coerenza con il tema (dark/light mode).

---

### 16. Duplicazione di codice tra `EmailTextField` e `PasswordTextField`

**File:** `ui/components/EmailTextField.kt`, `ui/components/PasswordTextField.kt`

Entrambi i componenti replicano quasi identicamente il layout (label in uppercase + spacer + OutlinedTextField con border personalizzato). `EasyGymTextField` è già il componente base, ma `EmailTextField` e `PasswordTextField` non lo usano — lo replicano.

**Soluzione:** far passare `EmailTextField` e `PasswordTextField` attraverso `EasyGymTextField` aggiungendo parametri per `keyboardType` e `visualTransformation`, oppure convertire tutto in un unico componente parametrizzato.

---

### 17. `PasswordTextField` manca dello `shape` nel `border`

**File:** `ui/components/PasswordTextField.kt:48-51`

```kotlin
.border(
    width = 1.5.dp,
    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
    // shape mancante!
)
```

`EmailTextField` e `EasyGymTextField` passano `shape = RoundedCornerShape(12.dp)`, ma `PasswordTextField` no. Questo produce angoli quadrati sul border del campo password, inconsistenti con il resto della UI.

---

### 18. `AthletesScreen`: errore e lista mostrati contemporaneamente

**File:** `ui/screens/athletes/AthletesScreen.kt:28-46`

```kotlin
Box(...) {
    state.errorMessage?.let { Text(it) }  // sopra
    LazyColumn { ... }                     // sotto, sempre visibile
}
```

Se c'è un errore, viene mostrato sopra la lista (che potrebbe essere vuota o no). L'utente vede sia il messaggio d'errore sia la lista vuota. La gestione degli stati dovrebbe essere mutualmente esclusiva: Loading | Error | Success.

---

### 19. `ClubScreen`: layout visivamente sovrapposto

**File:** `ui/screens/club/ClubScreen.kt:22-23`

```kotlin
Box(contentAlignment = Alignment.Center) {
    Text("Club Screen")      // centered
    PrimaryButton(...)       // centered sopra il Text
}
```

Entrambi gli elementi sono centrati nello stesso `Box`, il che significa che si sovrappongono. Il `Text` è invisibile dietro al pulsante.

---

### 20. `LazyColumn` con indice invece di `items(list)`

**File:** `ui/screens/athletes/AthletesScreen.kt:33-34`

```kotlin
items(state.users.size) { index ->
    val user = state.users[index]
```

Il modo idiomatico e più sicuro in Compose è:

```kotlin
items(state.users) { user ->
```

L'accesso per indice può causare `IndexOutOfBoundsException` se la lista cambia durante la composizione.

---

### 21. `@Preview` di `LoginScreen` richiede Hilt

**File:** `ui/screens/login/LoginScreen.kt:103-106`

```kotlin
@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen() // usa hiltViewModel() internamente
}
```

Questa preview fallisce nell'Android Studio Preview tool perché `hiltViewModel()` richiede un container Hilt. Le preview devono ricevere lo stato come parametro o usare un fake ViewModel.

**Soluzione consigliata:** separare la UI dallo stato tramite overload stateless:

```kotlin
@Composable
fun LoginScreen(
    state: LoginState,
    onLogin: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
)

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    LoginScreen(state, viewModel::login, viewModel::onEmailChanged, ...)
}
```

---

### 22. `ExportSchema = false` con `schemaLocation` configurato

**File:** `data/local/AppDatabase.kt:8`, `app/build.gradle.kts:61`

```kotlin
@Database(exportSchema = false, ...)  // schema non esportato
```

```kotlin
ksp { arg("room.schemaLocation", "$projectDir/schemas") }  // inutile se exportSchema = false
```

I due settings si contraddicono. Se le migrazioni sono importanti (e lo devono essere in produzione), impostare `exportSchema = true` e committare i file `.json` generati nel VCS per tracciare l'evoluzione dello schema.

---

### 23. JWT decodificato manualmente senza libreria

**File:** `ui/navigation/NavViewModel.kt:53-70`

Il parsing manuale del JWT tramite `Base64.decode` e `JSONObject` è fragile: non gestisce JWT malformati, padding Base64 errato, o claims mancanti in modo robusto. Il `runCatching` sopprime silenziosamente tutti gli errori.

**Soluzione:** usare una libreria dedicata (es. `java-jwt` di Auth0) oppure spostare la decodifica del token in un `UseCase` nel domain layer, lontano dal ViewModel.

---

### 24. Token salvati in chiaro nel DataStore

**File:** `data/repository/AuthRepositoryImpl.kt`

`DataStore<Preferences>` salva i dati in chiaro sul disco del dispositivo. I token JWT, specialmente quelli di refresh a lunga vita, andrebbero protetti.

**Soluzione:** usare `EncryptedSharedPreferences` (da `androidx.security.crypto`) oppure il `DataStore` con un serializer che cifra i dati tramite le Keystore APIs di Android.

---

## BASSO — Micro-ottimizzazioni e convenzioni

### 25. `object` invece di `data object` per le sealed class

**File:** `ui/navigation/NavDestination.kt`

Da Kotlin 1.9+ è disponibile `data object` per i singleton, che fornisce `toString()` leggibile e corretta implementazione di `equals`/`hashCode`.

```kotlin
data object LOADING : Common(route = "loading")
```

---

### 26. Stringhe hardcoded nell'UI

Messaggi come `"Compila tutti i campi"`, `"Email non valida"`, `"Errore: ${e.message}"`, etichette come `"Accedi"`, `"Login"`, `"Logout"` sono scritti direttamente nel codice Kotlin invece di essere in `res/values/strings.xml`. Questo rende impossibile la localizzazione e difficile la manutenzione.

---

### 27. `NavGraph` importa tutte le Screen direttamente

**File:** `ui/navigation/NavGraph.kt`

`NavGraph` importa e istanzia direttamente tutte le schermate. Con la crescita del progetto questo diventerà un file difficile da mantenere. Valutare l'introduzione di un sistema di feature modules o almeno suddividere i composable in extension function sul `NavGraphBuilder`.

---

## Riepilogo priorità

| Priorità | Numero | Descrizione |
|----------|--------|-------------|
| CRITICO  | 1 | `UserRole` nel layer UI usato da data e domain |
| CRITICO  | 2 | `AuthRepository` dipende da DTO data layer |
| CRITICO  | 3 | Bug navigazione: `startDestination` non reattivo |
| CRITICO  | 4 | Race condition e flusso errato in `AthletesViewModel` |
| CRITICO  | 5 | `UserEntity` con ID locale anziché ID del server |
| CRITICO  | 6 | Release build senza `BASE_URL` |
| ALTO     | 7 | `runBlocking` negli interceptor OkHttp |
| ALTO     | 8 | `println` invece di `Log` |
| ALTO     | 9 | Import `jakarta.inject` errato |
| ALTO     | 10 | `AppModule.kt` solo codice commentato |
| ALTO     | 11 | Minify disabilitato e `fallbackToDestructiveMigration` |
| ALTO     | 12 | Typo nella route "atlethes" |
| ALTO     | 13 | `LoadingGateViewModel` classe vuota |
| ALTO     | 14 | Navigazione type-unsafe con stringhe |
| MEDIO    | 15-24 | UI, duplicazione, layout, security, preview |
| BASSO    | 25-27 | Convenzioni Kotlin, stringhe, organizzazione |

---

*Revisione generata da Claude Code (claude-sonnet-4-6) — 05/03/2026*
