package com.example.hopitalainwzen.Login.Login.login

import com.example.hopitalainwzen.User

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: User? = null,
        val error: Int? = null
)