package com.example.ntnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ntnews.model.repository.AuthRepository
import com.example.ntnews.ui.components.BottomBar
import com.example.ntnews.ui.components.BottomNavItem
import com.example.ntnews.ui.navigation.Router
import com.example.ntnews.ui.theme.NTNewsTheme
import com.example.ntnews.ui.theme.NavyBlue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NTNewsTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController, authRepository = authRepository)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, authRepository: AuthRepository) {
    val currentBackStactEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStactEntry?.destination?.route

    val showMenu = remember { mutableStateOf(false) }

    val bottomNavItems = listOf(
        BottomNavItem(
            route = "feed",
            icon = Icons.Default.Home,
            label = "Home",
            onClick = { navController.navigate("feed") }
        ),
        BottomNavItem(
            route = "category",
            icon = Icons.Filled.Search,
            label = "Category",
            onClick = { navController.navigate("category") }
        ),
        BottomNavItem(
            route = "favorites",
            icon = Icons.Filled.DateRange,
            label = "Favorites",
            onClick = { navController.navigate("favorites") }
        )
    )
    Scaffold(
        topBar = {
            if (currentDestination == "feed" || currentDestination == "favorites" || currentDestination == "category") {
                TopAppBar(
                    title = { Text("NT News") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White, // Background color
                        titleContentColor = Color.Black // Title text color
                    ),
                    actions = {
                        IconButton(onClick = { showMenu.value = true }) {
                            androidx.compose.material3.Icon(Icons.Default.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier.size(25.dp))
                        }

                        DropdownMenu(
                            expanded = showMenu.value,
                            onDismissRequest = { showMenu.value = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    ) {
                                        Text("Sign Out", color = NavyBlue, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                                    }
                                },
                                onClick = {
                                    authRepository.signOut()
                                    navController.navigate("login") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                    showMenu.value = false
                                }
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentDestination == "feed" || currentDestination == "favorites" || currentDestination == "category") {
                BottomBar(navController = navController, bottomNavItems = bottomNavItems, onItemClick = { navController.navigate(it) })
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Router(navController = navController, authRepository = authRepository)
            }
        }
    )
}