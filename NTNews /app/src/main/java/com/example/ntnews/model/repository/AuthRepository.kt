package com.example.ntnews.model.repository

import com.example.ntnews.model.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) {
    private var cachedUserData :User? =null

    suspend fun signIn(email: String?, password: String?): Result<FirebaseUser> {
        if (email == null || password == null) {
            return Result.failure(IllegalArgumentException("Email or password cannot be null"))
        }
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loadUserData(userID: String): Result<User> {
        return try {
            val document = db.collection("Users").document(userID).get().await()

            if (document.exists()) {
                val userData = document.toObject(User::class.java)

                userData?.let {it ->
                    cachedUserData = it
                    Result.success(it)
                } ?: Result.failure(Exception("User data is null"))
            } else {
                Result.failure(Exception("User data not found"))
            }
        } catch (e: FirebaseFirestoreException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun getCachedUserData(): User? {
        return cachedUserData
    }



    fun sendPasswordResetEmail(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.message ?: "Failed to send password reset email")
            }
        }
    }


    fun signOut() {
        auth.signOut()
    }


    fun checkUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }


    fun getCurrentUserID(): String? {
        val currentUser = auth.currentUser
        return currentUser?.uid
    }


    suspend fun loadCurrentUserData(): Result<User> {
        val userId = getCurrentUserID()
        return if (userId != null) {
            loadUserData(userId)
        } else {
            Result.failure(Exception("No user logged in"))
        }
    }
}
