package com.example.ntnews.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ntnews.data.preferences.PreferencesManager
import com.example.ntnews.ui.theme.GrayBlue
import com.example.ntnews.ui.theme.NavyBlue
import com.example.ntnews.ui.theme.Redwood
import com.example.ntnews.ui.theme.SoftBlue
import com.example.ntnews.utils.LoginState
import com.example.ntnews.viewmodel.AuthViewModel
import com.example.ntnews.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    preferencesManager: PreferencesManager = PreferencesManager(LocalContext.current)
) {
    var firstName = remember{ mutableStateOf("") }
    var lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf(preferencesManager.getEmail() ?: "") }
    var password = remember { mutableStateOf("") }
    val showResetPasswordDialog = remember { mutableStateOf(false) }
    val loginState = viewModel.loginState.collectAsState()
    var confirmPassword = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val passwordVisible = remember { mutableStateOf(false) }
    val rememberMeChecked = remember { mutableStateOf(preferencesManager.isRememberMeChecked()) }
    val snackbarHostState = remember { SnackbarHostState() }



    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF1A1A2E)),
        contentAlignment = Alignment.BottomCenter)
    {
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            Card (modifier = Modifier
                .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Login",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        color= Color(0xFF1A1A2E)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(value = email.value, onValueChange = {email.value=it},
                        label = { Text(text = "Email", fontSize = 16.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(emailFocusRequester),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    OutlinedTextField(value = password.value, onValueChange ={password.value=it} , label = { Text("Password", fontSize = 16.sp) }, modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester),
                        shape = RoundedCornerShape(8.dp),
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image =if (passwordVisible.value){
                                Icons.Default.Close
                            }else{
                                Icons.Default.Done
                            }
                            IconButton(onClick = {
                                passwordVisible.value=!passwordVisible.value
                            }) {
                                Icon(imageVector = image, contentDescription = null)

                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 8.dp, top = 8.dp)
                    ) {
                        Checkbox(
                            checked = rememberMeChecked.value,
                            onCheckedChange = { rememberMeChecked.value = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.DarkGray,
                                uncheckedColor = Color.Gray,
                                checkmarkColor = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Remember Me",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(onClick = {
                        if (password.value.length<8){
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Please enter a valid password!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }else{
                            if (rememberMeChecked.value){
                                preferencesManager.saveLoginData(email.value,true)
                            }else{
                                preferencesManager.clearLoginData()
                            }
                            viewModel.login(email.value,password.value, onSuccess = {} )
                        }
                    },
                        shape = RoundedCornerShape(45.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Login", modifier = Modifier.padding(8.dp), fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(onClick = { navController.navigate("signup") }) {
                        Text(text = "Don't have an account? Sign Up", fontSize = 16.sp, color = NavyBlue)
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    TextButton(onClick = { showResetPasswordDialog.value = true }) {
                        Text(text = "Forgot Password", fontSize = 16.sp, color = NavyBlue)
                    }
                }
            }
            if (showResetPasswordDialog.value) {
                AlertDialog(
                    onDismissRequest = { showResetPasswordDialog.value = false },
                    title = {
                        Text("Reset Password", color = NavyBlue, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    },
                    text = {
                        Column {
                            Text(
                                "Please enter your email to receive password reset instructions.",
                                color = Color.DarkGray,
                                fontSize = 16.sp
                            )
                            OutlinedTextField(
                                value = email.value,
                                onValueChange = { email.value = it },
                                label = { Text("Email", color = Color.Gray) },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                authViewModel.forgotPassword(email.value)
                                showResetPasswordDialog.value = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GrayBlue, contentColor = Color.White)
                        ) {
                            Text("Send")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showResetPasswordDialog.value = false }) {
                            Text("Cancel", color = NavyBlue)
                        }
                    },
                    containerColor = Color.White.copy(0.9f),
                    shape = RoundedCornerShape(25.dp)
                )
            }
            LaunchedEffect(key1 = loginState.value) {
                when (loginState.value) {
                    is LoginState.Loading -> {
                        // Optionally handle loading state
                    }
                    is LoginState.Success -> {
                        authViewModel.loadUserData()
                        navController.navigate("feed")
                    }
                    is LoginState.Error -> {
                        snackbarHostState.showSnackbar(
                            message = (loginState.value as LoginState.Error).error,
                            duration = SnackbarDuration.Short
                        )
                    }
                    else -> {
                        Log.d("SignInScreen", "Unknown state: ${loginState.value}")
                    }
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = Redwood,
                    contentColor = Color.White
                )
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}

