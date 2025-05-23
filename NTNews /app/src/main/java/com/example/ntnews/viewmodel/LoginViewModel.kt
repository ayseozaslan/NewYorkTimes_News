package com.example.ntnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ntnews.model.repository.AuthRepository
import com.example.ntnews.utils.LoginState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val auth: FirebaseAuth
): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)


    val loginState = _loginState.asStateFlow()


    fun login(email: String, password: String, onSuccess: () -> Unit) {

        _loginState.value = LoginState.Loading


        viewModelScope.launch {
            val result = authRepository.signIn(email, password)


            if (result.isSuccess) {
                onSuccess()
                authRepository.loadUserData(auth.currentUser!!.uid)
                _loginState.value = LoginState.Success("Sign in successful!")
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                _loginState.value = LoginState.Error(errorMessage)
            }
        }
    }
    fun resetSignInState() {
        _loginState.value = LoginState.Idle
    }

    }

