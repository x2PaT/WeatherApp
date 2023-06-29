package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.screens.SplashScreen


@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) { SplashScreen(navController) }
//        composable(WeatherScreens.MainScreen.name) { MainScreen(navController) }
//        composable(WeatherScreens.FavoritesScreen.name) { FavoritesScreen(navController) }
//        composable(WeatherScreens.SearchScreen.name) { SearchScreen(navController) }
//        composable(WeatherScreens.AboutScreen.name) { AboutScreen(navController) }
//        composable(WeatherScreens.SearchScreen.name) { SearchScreen(navController) }
    }
}