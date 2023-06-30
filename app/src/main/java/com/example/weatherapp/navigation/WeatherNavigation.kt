package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.screens.main.MainScreen
import com.example.weatherapp.screens.main.MainScreenViewModel
import com.example.weatherapp.screens.splash.SplashScreen


@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) { SplashScreen(navController) }
        composable(WeatherScreens.MainScreen.name) {
            val mainScreenViewModel = hiltViewModel<MainScreenViewModel>()
            MainScreen(navController, mainScreenViewModel) }
//        composable(WeatherScreens.FavoritesScreen.name) { FavoritesScreen(navController) }
//        composable(WeatherScreens.SearchScreen.name) { SearchScreen(navController) }
//        composable(WeatherScreens.AboutScreen.name) { AboutScreen(navController) }
//        composable(WeatherScreens.SearchScreen.name) { SearchScreen(navController) }
    }
}