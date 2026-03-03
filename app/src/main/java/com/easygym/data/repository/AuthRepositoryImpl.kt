package com.easygym.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.easygym.data.remote.auth.Auth
import com.easygym.data.remote.auth.AuthDTO
import com.easygym.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

    private object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override val accessToken: Flow<String?> =
        dataStore.data
            .map { prefs ->
                prefs[Keys.ACCESS_TOKEN]
            }

    override val refreshToken: Flow<String?> =
        dataStore.data
            .map { prefs ->
                prefs[Keys.REFRESH_TOKEN]
            }

    override suspend fun logout() {
        dataStore.edit { prefs ->
            prefs.remove(Keys.ACCESS_TOKEN)
            prefs.remove(Keys.REFRESH_TOKEN)
        }
    }

    override suspend fun login(loginRequest: AuthDTO.Login) {
        val loginResponse = auth.login(loginRequest)
        dataStore.edit { prefs ->
            prefs[Keys.ACCESS_TOKEN] = loginResponse.accessToken
            prefs[Keys.REFRESH_TOKEN] = loginResponse.refreshToken
        }
        println("Login successful: $loginResponse")
    }

    override suspend fun refresh(refreshRequest: AuthDTO.Refresh) {
        val refreshResponse = auth.refresh(refreshRequest)
        dataStore.edit { prefs ->
            prefs[Keys.ACCESS_TOKEN] = refreshResponse.accessToken
            prefs[Keys.REFRESH_TOKEN] = refreshResponse.refreshToken
        }
        println("Refresh successful: $refreshResponse")
    }
}