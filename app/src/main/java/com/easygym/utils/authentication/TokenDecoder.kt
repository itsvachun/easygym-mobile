package com.easygym.utils.authentication

import android.util.Base64
import com.easygym.utils.enums.UserRole
import org.json.JSONObject
import javax.inject.Inject

class TokenDecoder @Inject constructor() {

    fun getRoleFromToken(token: String?): UserRole? {
        if (token.isNullOrBlank()) return null
        return runCatching {
            val parts = token.split(".")
            if (parts.size < 2) return null
            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            val json = JSONObject(payload)
            UserRole.valueOf(json.getString("role"))
        }.getOrNull()
    }

    fun isTokenExpired(token: String?): Boolean {
        if (token.isNullOrBlank()) return true
        return runCatching {
            val parts = token.split(".")
            if (parts.size < 2) return true
            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            val json = JSONObject(payload)
            val exp = json.getLong("exp")
            val currentTime = System.currentTimeMillis() / 1000
            currentTime >= exp
        }.getOrDefault(true)
    }
}
