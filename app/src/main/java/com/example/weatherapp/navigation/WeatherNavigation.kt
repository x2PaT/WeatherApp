package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.screens.about.AboutScreen
import com.example.weatherapp.screens.favorites.FavoritesScreen
import com.example.weatherapp.screens.main.MainScreen
import com.example.weatherapp.screens.main.MainScreenViewModel
import com.example.weatherapp.screens.search.SearchScreen
import com.example.weatherapp.screens.settings.SettingsScreen
import com.example.weatherapp.screens.splash.SplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        val route = WeatherScreens.MainScreen.name
        composable(
            "$route/{city}",
            arguments = listOf(navArgument(name = "city") { type = NavType.StringType }),
        ) {
            it.arguments?.getString("city").let { city ->
                val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
                MainScreen(
                    navController,
                    mainScreenViewModel,
                    city = city,
                )
            }
        }
        composable(WeatherScreens.SearchScreen.name) { SearchScreen(navController) }
        composable(WeatherScreens.FavoritesScreen.name) { FavoritesScreen(navController) }
        composable(WeatherScreens.AboutScreen.name) { AboutScreen(navController) }
        composable(WeatherScreens.SettingsScreen.name) { SettingsScreen(navController) }
    }
}

