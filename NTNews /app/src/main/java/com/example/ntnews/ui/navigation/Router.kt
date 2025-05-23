package com.example.ntnews.ui.navigation

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StampedPathEffectStyle.Companion.Morph
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ntnews.model.entities.NewsItem
import com.example.ntnews.model.repository.AuthRepository
import com.example.ntnews.ui.screens.CategoryScreen
import com.example.ntnews.ui.screens.FavoriteScreen
import com.example.ntnews.ui.screens.FeedScreen
import com.example.ntnews.ui.screens.LoginScreen
import com.example.ntnews.ui.screens.NewsDetailScreen
import com.example.ntnews.ui.screens.SignUpScreen
import com.example.ntnews.utils.ApiResult
import com.example.ntnews.viewmodel.CategoryViewModel
import com.example.ntnews.viewmodel.LoginViewModel
import com.example.ntnews.viewmodel.SignUpViewModel
import kotlinx.coroutines.delay


@Composable
fun Router(navController: NavHostController, authRepository: AuthRepository) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController, authRepository)
        }
        composable("signup") {
            val signUpViewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(navController, signUpViewModel)
        }


        composable("login") {
            val signInViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController, signInViewModel)
        }


        composable("feed") {
            FeedScreen(navController)
        }

        composable(
            "newsDetail/{newsItemId}",
            arguments = listOf(
                navArgument("newsItemId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val newsItemId = backStackEntry.arguments?.getString("newsItemId")
            NewsDetailScreen(navController = navController, newsItemId = newsItemId ?: "")
        }


        composable("category") {
          CategoryScreen(navController)
        }

        composable("favorites") {
            FavoriteScreen(navController)
        }

    }
}

@Composable
fun SplashScreen(navController: NavHostController,authRepository: AuthRepository) {
    LaunchedEffect(key1 = true) {
        delay(2000)

        val destination= if (authRepository.checkUserLoggedIn())
            "feed"
        else
            "login"

        navController.navigate(destination) {
            popUpTo("splash") { inclusive = true }
        }

    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .border(3.dp, color = Color.Black)
                    .background(color = Color(0xFF1A1A2E)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "NT News",
                   style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    fontSize = 30.sp
                )
            }
        }
    }
}
