# Code Review — EasyGym Mobile

Revisione del codice sorgente del branch `manage-users`.

---

## Architettura Generale

La struttura Clean Architecture (data / domain / ui) è ben impostata. La DI con Hilt è configurata correttamente e l'uso di Repository + DataSource è coerente. Di seguito i problemi trovati, ordinati per gravità.

---

## Problemi Critici

### 1. `UserRole` definito nel layer UI — violazione Clean Architecture
**File:** `app/src/main/java/com/easygym/ui/navigation/NavViewModel.kt:14`

`UserRole` è definito in `com.easygym.ui.navigation` ma viene usato in `domain/model/User.kt` e `data/local/entity/UserEntity.kt`. Il layer domain non dovrebbe dipendere dal layer UI. `UserRole` va spostato in `domain/model/`.

---

### 2. `AuthRepository` (domain) importa classi dal layer data
**File:** `app/src/main/java/com/easygym/domain/repository/AuthRepository.kt:4-5`

```kotlin
import com.easygym.data.remote.model.auth.LoginRequest
import com.easygym.data.remote.model.auth.RefreshRequest
```

Il domain non dovrebbe dipendere dal layer data. `LoginRequest` e `RefreshRequest` dovrebbero essere modelli del domain, oppure la firma del metodo `login()` dovrebbe accettare tipi primitivi (`email: String, password: String`).

---

### 3. `UserEntity` — ID instabile, deduplicazione rotta
**File:** `app/src/main/java/com/easygym/data/local/entity/UserEntity.kt:12`

```kotlin
val id: String = UUID.randomUUID().toString(),
```

L'ID viene generato casualmente ad ogni istanza. Quando `UserRepositoryImpl.getAll()` inserisce i nuovi dati con `REPLACE`, invece di aggiornare i record esistenti crea sempre duplicati (o li sostituisce in modo casuale). L'ID dovrebbe provenire dall'API.

---

### 4. `AthletesViewModel` — race condition nel `init`
**File:** `app/src/main/java/com/easygym/ui/screens/athletes/AthletesViewModel.kt:27-39`

```kotlin
init {
    viewModelScope.launch {
        try {
            userRepository.getAll()   // fetch remoto
            _state.update { ... }
        } catch (e: Exception) { ... }

        userRepository.users.collect { ... }  // questo blocca
    }
}
```

`getAll()` e `collect` sono nello stesso `launch`: se `getAll()` fallisce, `collect` viene comunque avviato correttamente, ma `isLoading` rimane `true` fino al primo emit del flow. E' piu' robusto lanciare due coroutine separate: una per il fetch remoto e una per osservare il flow locale.

---

### 5. Import errato in `UserRepositoryImpl`
**File:** `app/src/main/java/com/easygym/data/repository/UserRepositoryImpl.kt:7`

```kotlin
import jakarta.inject.Inject  // SBAGLIATO
```

Su Android si usa `javax.inject.Inject`. `jakarta.inject` e' per Jakarta EE e non garantisce compatibilita'.

---

## Problemi Rilevanti

### 6. `NavGraph` — `startDestination` dinamico
**File:** `app/src/main/java/com/easygym/ui/navigation/NavGraph.kt:48-52`

Cambiare `startDestination` di `NavHost` in base allo stato non e' supportato correttamente da Compose Navigation: il `NavHost` non reagisce ai cambiamenti di `startDestination` dopo la composizione iniziale. La navigazione reattiva va gestita con `LaunchedEffect` + `navController.navigate()`.

---

### 7. Decodifica JWT lato client per determinare il ruolo
**File:** `app/src/main/java/com/easygym/ui/navigation/NavViewModel.kt:50-72`

Decodificare il JWT nel client per estrarre ruolo e scadenza e' fragile: basta un token manomesso per ottenere permessi sbagliati nell'UI. Il ruolo dovrebbe venire dall'API e la verifica del token va fatta server-side. Questo approccio va bene solo per la navigazione UI ma non deve mai essere usato per decisioni di sicurezza.

---

### 8. `TokenAuthenticator` — nessuna gestione errori sul refresh
**File:** `app/src/main/java/com/easygym/services/TokenAuthenticator.kt:29-32`

```kotlin
val newAccessToken = runBlocking {
    authRepository.get().refresh(RefreshRequest(refreshToken))
    authRepository.get().accessToken.first()
}
```

Se `refresh()` lancia un'eccezione, il `runBlocking` crasha senza gestione. Andrebbero usati `try/catch` o `runCatching`, e in caso di fallimento va eseguito il logout.

---

### 9. `UserDAO` — nessuna cancellazione prima del reload
**File:** `app/src/main/java/com/easygym/data/repository/UserRepositoryImpl.kt:16-19`

Quando si ricaricano gli utenti dal server, si fa solo `insertAll` (con `REPLACE`). Se un utente viene eliminato server-side, rimarra' nel database locale indefinitamente. Prima di `insertAll` andrebbe eseguita una `DELETE FROM users`, o meglio, usare una strategia di sincronizzazione differenziale.

---

### 10. `DatabaseModule` — `fallbackToDestructiveMigration` in produzione
**File:** `app/src/main/java/com/easygym/di/DatabaseModule.kt:19`

```kotlin
.fallbackToDestructiveMigration()
```

Cancella tutti i dati dell'utente ad ogni migrazione del DB. Accettabile in sviluppo, **non** in produzione. Aggiungere migrazioni esplicite prima del rilascio.

---

## Problemi Minori

### 11. Typo nel route di ATHLETES
**File:** `app/src/main/java/com/easygym/ui/navigation/NavDestination.kt:17`

```kotlin
object ATHLETES : BottomBar(route = "atlethes", ...)  // "atlethes" invece di "athletes"
```

---

### 12. `println` invece di `Log` di Android
**File:** `app/src/main/java/com/easygym/data/repository/AuthRepositoryImpl.kt:57,65`

```kotlin
println("Login successful: $loginResponse")
println("Refresh successful: $refreshResponse")
```

Su Android usare `Log.d("AuthRepo", ...)`. I `println` non sono filtrabili con Logcat e non vengono rimossi automaticamente in release build (a meno di ProGuard specifico).

---

### 13. `LoginState.isLoggedIn` non viene mai usato
**File:** `app/src/main/java/com/easygym/ui/screens/login/LoginViewModel.kt:18`

Il campo `isLoggedIn` in `LoginState` non viene mai impostato a `true`. La navigazione post-login e' gestita da `NavViewModel` che osserva il token: il campo e' dead code.

---

### 14. `LoginViewModel` — validazione password assente
**File:** `app/src/main/java/com/easygym/ui/screens/login/LoginViewModel.kt:34-44`

Viene validata l'email ma non la password. Aggiungere almeno un controllo `isBlank()` sulla password per coerenza con la validazione dell'email.

---

### 15. `LoginScreen` — loading state non gestito nella UI
**File:** `app/src/main/java/com/easygym/ui/screens/login/LoginScreen.kt`

`LoginState.isLoading` viene settato nel ViewModel ma la schermata non mostra alcun indicatore di caricamento ne' disabilita il pulsante "Accedi" durante la richiesta, permettendo click multipli.

---

### 16. `AthletesScreen` — errore e lista mostrati simultaneamente
**File:** `app/src/main/java/com/easygym/ui/screens/athletes/AthletesScreen.kt:28-44`

Il messaggio di errore e la `LazyColumn` con i dati in cache sono sovrapposti nello stesso `Box`. Se c'e' un errore parziale con dati locali, l'utente vede entrambi senza chiarezza. Gestire i tre stati (loading / error / success) in modo esclusivo.

---

### 17. `AthletesScreen` — `LazyColumn` senza `key`
**File:** `app/src/main/java/com/easygym/ui/screens/athletes/AthletesScreen.kt:33`

```kotlin
items(state.users.size) { index -> ... }
```

Usare `items(state.users, key = { it.someId }) { user -> ... }` per migliorare le performance e permettere animazioni corrette durante gli aggiornamenti della lista.

---

### 18. `AppModule.kt` — codice commentato da rimuovere
**File:** `app/src/main/java/com/easygym/di/AppModule.kt`

Il modulo e' quasi completamente commentato (la logica e' stata spostata in `DatabaseModule`). Il file puo' essere eliminato o ripulito.

---

## Riepilogo

| Severita' | Numero |
|---|---|
| Critico | 5 |
| Rilevante | 5 |
| Minore | 8 |

**Priorita' principali:**
1. Spostare `UserRole` nel domain layer
2. Correggere la dipendenza invertita in `AuthRepository`
3. Fixare l'ID instabile in `UserEntity`
4. Correggere l'import `jakarta` -> `javax` in `UserRepositoryImpl`
5. Aggiungere gestione errori nel `TokenAuthenticator`
