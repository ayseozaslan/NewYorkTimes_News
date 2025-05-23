import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.ntnews.viewmodel.SignUpState

@Composable
fun SignUpScreen(navController: NavController) {
    val signUpState = remember { mutableStateOf<SignUpState>(SignUpState.Initial) }

    LaunchedEffect(key1 = signUpState.value) {
        when (signUpState.value) {
            is SignUpState.Success -> {
                navController.navigate("feed")
            }
            // ... handle other states ...
        }
    }

    // ... rest of the composable code ...
} 