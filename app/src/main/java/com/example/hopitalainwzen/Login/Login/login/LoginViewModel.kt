package com.example.hopitalainwzen.Login.Login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.hopitalainwzen.Login.Login.LoginRepository

import com.example.hopitalainwzen.R
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            try {
                val result = loginRepository.login(username, password)
                loginRepository.fetchToken()
                //loginRepository.passingType(result.userId)
                loginRepository.updateUserToken(result.userId)
                loginRepository.user?.let {
                    _loginResult.postValue(LoginResult(it))
                } ?: _loginResult.postValue(LoginResult(error = R.string.login_failed))
            } catch (e: Exception){
                println(e)
                when (e) {
//                    is FirebaseAuthInvalidCredentialsException -> {}//display our own message
//                    is FirebaseAuthInvalidUserException -> {}
//                    else -> {}
                }
            }
        }
    }
    //fun signup(username: String, password: String, displayName: String, doctorProperties: DoctorProperties, patientProperties: PatientProperties)
    fun signup(username: String, password: String, displayName: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            try {
                val result = loginRepository.signup(username, password)
                loginRepository.fetchToken()
                //loginRepository.passingType(result.userId)

                //loginRepository.updateUserInfo(result.userId, displayName, "patient", doctorProperties, patientProperties)
                loginRepository.updateUserInfo(result.userId, displayName, "patient")
                loginRepository.user?.let {
                    _loginResult.postValue(LoginResult(it))
                } ?: _loginResult.postValue(LoginResult(error = R.string.login_failed))

            } catch (e: Exception) {
                println(e)
                when (e) {
//                    is FirebaseAuthInvalidCredentialsException -> {}//display our own message
//                    is FirebaseAuthInvalidUserException -> {}
//                    else -> {}
                }
            }
        }
    }

    fun loginDataChanged(username: String, password: String, displayName: String? = null) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun registerDataChanged(username: String, password: String, displayName: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else if (!isDisplayNameValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_display_name)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder display name validation check
    private fun isDisplayNameValid(displayName: String): Boolean {
        return !displayName.isNullOrEmpty()
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
    fun passingType(userId:String): Deferred<String?>?{
        val typee: Deferred<String?>? =
        viewModelScope.async {
            loginRepository.passingType(userId )
        }
        return typee
    }


}