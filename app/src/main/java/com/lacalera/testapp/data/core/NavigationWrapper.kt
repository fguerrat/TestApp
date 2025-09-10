package com.lacalera.testapp.data.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lacalera.testapp.presentation.home.HomeRoot
import com.lacalera.testapp.presentation.home.HomeScreen
import com.lacalera.testapp.presentation.splash.SplashScreen
import com.lobito.utilscalera.data.core.navigation.Home
import com.lobito.utilscalera.data.core.navigation.Splash

@Composable
fun NavigationWrapper(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(0) // Clear back stack up to and including Splash
                        launchSingleTop = true
                    }
                },
            )
        }

        composable<Home> {
            HomeRoot()
        }
    }
}
