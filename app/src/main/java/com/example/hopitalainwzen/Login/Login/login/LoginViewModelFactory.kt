package com.example.hopitalainwzen.Login.Login.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hopitalainwzen.Login.Login.LoginDataSource
import com.example.hopitalainwzen.Login.Login.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(var sharedPref: SharedPreferences) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                    loginRepository = LoginRepository(
                            dataSource = LoginDataSource(),
                            sharedPref = sharedPref
            )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}