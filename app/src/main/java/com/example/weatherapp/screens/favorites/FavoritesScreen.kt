package com.example.weatherapp.screens.favorites

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.widgets.TopAppBarWidget

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    favoritesScreenViewModel: FavoritesScreenViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBarWidget(
                title = "Favorites",
                isMainScreen = false,
                navController = navController,
                onLeadingButtonClicked = {
                    navController.popBackStack()
                },
            )
        },
    ) {
        Surface(modifier = Modifier.padding(it)) {
            Column {
                val list = favoritesScreenViewModel.favoritesList.collectAsState().value

                LazyColumn {
                    items(items = list) {
                        CityRow(it, navController, favoritesScreenViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    favoritesScreenViewModel: FavoritesScreenViewModel,
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(
                    WeatherScreens.MainScreen.name + "/${favorite.city}",
                )
            },
        shape = CircleShape.copy(
            topEnd = CornerSize(6.dp),
        ),
        color = Color.LightGray,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favorite.city)

            Surface(
                modifier = Modifier,
                shape = CircleShape,
                color = Color.White,
            ) {
                Text(
                    text = favorite.country,
                    modifier = Modifier.padding(4.dp),
                )
            }
            IconButton(
                onClick = { favoritesScreenViewModel.removeFavorite(favorite) },
            ) {
                Icon(
                    tint = Color.Red,
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete icon",
                )
            }
        }
    }
}

