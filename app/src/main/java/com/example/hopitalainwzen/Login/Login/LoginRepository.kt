package com.example.hopitalainwzen.Login.Login

import android.content.SharedPreferences
import com.example.hopitalainwzen.DoctorProperties
import com.example.hopitalainwzen.LoggedInUser
import com.example.hopitalainwzen.PatientProperties
import com.example.hopitalainwzen.User



/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource, var sharedPref: SharedPreferences) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }
    suspend fun fetchToken(): String {
        token?.let {
            return it
        } ?: run{
            var instanceIdToken = dataSource.fetchToken()
            setInstanceIdToken(instanceIdToken)
            return instanceIdToken
        }
    }


    suspend fun login(email: String, password: String) : LoggedInUser {
        // handle login
        return dataSource.login(email, password)


    }

    private fun setLoggedInUser(user: User) {
        this.user = user
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    suspend fun signup(email: String, password: String): LoggedInUser {
        // handle registration
        return dataSource.register(email, password)
    }
    suspend fun updateUserInfo(userId: String, name: String, userType: String) {
        var user = User(token = token, name = name, userId = userId, type = userType,doctorProperties = DoctorProperties(),patientProperties = PatientProperties())
        dataSource.updateUserInfo(user!!)
        setLoggedInUser(user)
    }

    suspend fun updateUserToken(userId: String) {
            token?.let {token ->
                dataSource.updateUserToken(token, userId)?.let {
                    setLoggedInUser(it)
                }
            }
    }

    fun setInstanceIdToken(token: String) {
        this.token = token
    }


    private var token: String?
        get() {
            return sharedPref?.getString("token", null)
        }
        set(value) {
            sharedPref?.edit()?.putString("token", value)?.apply()
        }
    suspend fun passingType(userId: String):String?{
        return dataSource.passingType(userId)
    }


}