package com.example.ntnews.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ntnews.ui.theme.GrayBlue
import com.example.ntnews.ui.theme.SoftBlue
import com.example.ntnews.utils.SignUpState
import com.example.ntnews.viewmodel.SignUpViewModel


@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {

    val firstName = remember{ mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val localFocusManager= LocalFocusManager.current
    val signUpState = viewModel.signUpState.collectAsState()


    val context = LocalContext.current

    val passwordVisible = remember { mutableStateOf(false) }
    val confirmPasswordVisible = remember { mutableStateOf(false) }

    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    fun isPasswordValid(password: String): Boolean {
        // En az 8 karakter kontrolü
        if (password.length < 8) {
            return false
        }

        // Ardışık sayı kontrolü
        for (i in 0 until password.length - 2) {
            val first = password[i]
            val second = password[i + 1]
            val third = password[i + 2]

            // Ardışık sayılar kontrolü
            if (first.isDigit() && second.isDigit() && third.isDigit()) {
                if (second - first == 1 && third - second == 1) {
                    return false // Ardışık sayılar bulundu
                }
            }
        }

        return true
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF1A1A2E)),
        contentAlignment = Alignment.BottomCenter)
    {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text( text = "Sign Up",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Blue)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = firstName.value,
                onValueChange = { firstName.value = it },
                label = { Text("First name", fontSize = 16.sp) },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
            )

                Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                value = lastName.value,
                onValueChange = { lastName.value = it },
                label = { Text("Last name", fontSize = 16.sp) },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email", fontSize = 16.sp) },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))

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
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = confirmPassword.value, onValueChange ={confirmPassword.value=it} , label = { Text("Confirm Password", fontSize = 16.sp) }, modifier = Modifier
                .fillMaxWidth()
                .focusRequester(confirmPasswordFocusRequester),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image =if (confirmPasswordVisible.value){
                        Icons.Default.Close
                    }else{
                        Icons.Default.Done
                    }
                    IconButton(onClick = {
                        confirmPasswordVisible.value=!confirmPasswordVisible.value
                    }) {
                        Icon(imageVector = image, contentDescription = null)

                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    if (!isPasswordValid(password.value)) {
                        Toast.makeText(context, "Password must be at least 8 characters long and not contain sequential numbers!", Toast.LENGTH_SHORT).show()
                    } else if (password.value == confirmPassword.value) {
                        viewModel.signUp(email.value, password.value, firstName.value, lastName.value)
                    } else {
                        Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(45),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up")
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Already have an account? Login here!")
            }


        }
    }

    when (signUpState.value) {
        is SignUpState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
                Log.d("SignUpScreen", "Current SignUpState: ${signUpState.value}")
            }
        }
        is SignUpState.Success -> {
            Toast.makeText(context, (signUpState.value as SignUpState.Success).message, Toast.LENGTH_LONG).show()
            Log.d("SignUpScreen", "Navigating to signin")
            navController.navigate("feed") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
            viewModel.resetSignUpState() // Durumu sıfırlayın
        }
        is SignUpState.Error -> {
            Toast.makeText(context, (signUpState.value as SignUpState.Error).error, Toast.LENGTH_LONG).show()
            viewModel.resetSignUpState() // Durumu sıfırlayın
        }
        else -> {
            Log.d("SignUpScreen", "Unknown state: ${signUpState.value}")
        }
    }
}








