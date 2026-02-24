package com.easygym.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.easygym.data.remote.auth.Auth
import com.easygym.data.remote.auth.model.Login
import com.easygym.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

    private object Keys {
        val JWT_KEY = stringPreferencesKey("jwt_token")
    }

    override val jwtToken: Flow<String?> =
        dataStore.data
            .map { prefs ->
                prefs[Keys.JWT_KEY]
            }

    override suspend fun clearJwtToken() {
        dataStore.edit { prefs ->
            prefs.remove(Keys.JWT_KEY)
        }
    }

    override suspend fun login(loginRequest: Login.Request) {
        val loginResponse = auth.login(loginRequest)
        dataStore.edit { prefs ->
            prefs[Keys.JWT_KEY] = loginResponse.accessToken
        }
        println("Login successful: $loginResponse")
    }
}