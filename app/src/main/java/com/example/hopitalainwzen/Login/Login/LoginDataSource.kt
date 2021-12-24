package com.example.hopitalainwzen.Login.Login


import com.example.hopitalainwzen.LoggedInUser
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private var auth: FirebaseAuth = Firebase.auth
    val usersCollectionRef = Firebase.firestore
    suspend fun login(email: String, password: String): LoggedInUser {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            return LoggedInUser(result.user.uid, result.user.email)
    }

    fun logout() {
        auth.signOut()
    }

    suspend fun register(email: String, password: String): LoggedInUser {
        // [START create_user_with_email]
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            return LoggedInUser(result.user.uid, result.user.email)
        // [END create_user_with_email]
    }


    suspend fun updateUserInfo(user: com.example.hopitalainwzen.User) {
        user.userId?.let {
            getUserDocument(it)?.apply {
                reference.set(user).await()
            } ?: addUserInfo(user)
        }
    }

    suspend fun updateUserToken(token: String, userId: String): com.example.hopitalainwzen.User? {
        val querySnapshot = usersCollectionRef.collection("users").whereEqualTo("userId",userId).get().await()
        val document = querySnapshot.documents.firstOrNull()
        val value = hashMapOf("token" to token)
        document?.reference?.set(value, SetOptions.merge())
        return document?.toObject<com.example.hopitalainwzen.User>()


    }
    suspend fun addUserInfo(user: com.example.hopitalainwzen.User) {
        usersCollectionRef.collection("users").add(user).await()
    }

    suspend fun getUserInfo(userId: String): com.example.hopitalainwzen.User? {
        getUserDocument(userId)?.let {
            return it.toObject<com.example.hopitalainwzen.User>()
        } ?: return null
    }
    private suspend fun getUserDocument(userId: String): DocumentSnapshot? {
        val query =
                usersCollectionRef.collection("users").whereEqualTo("userId", userId).get().await()
        return if (query.isEmpty) {
            null
        } else {
            query.documents.first()
        }
    }
    suspend fun fetchToken(): String {
        try {
            val instanceIdResult = FirebaseInstanceId.getInstance().instanceId.await()
            return instanceIdResult.token
        } catch (e: java.lang.Exception) {
            throw e
        }
    }
    suspend fun passingType( userId: String):String? {
        val querySnapshot = usersCollectionRef.collection("users").whereEqualTo("userId",userId).get().await()
        val document = querySnapshot.documents.firstOrNull()

        return document?.toObject<com.example.hopitalainwzen.User>()?.type

    }


}