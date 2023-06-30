package com.example.weatherapp.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.widgets.TopAppBarWidget
import java.lang.Exception

@Composable
fun MainScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = false)
    ) {
        value = mainScreenViewModel.getWeatherData("Seattle")
    }.value

    if (weatherData.loading == true) {
        Column(
            modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "Loading... ${weatherData.loading}")
        }
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(
    weather: Weather,
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBarWidget(
                title = "${weather.city.name}, ${weather.city.country}",
                navController = navController,
                elevation = 5.dp
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatDate(weather.list[0].dt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(6.dp)
            )

            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .size(200.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WeatherStateImage(imageCode = weather.list[0].weather[0].icon)
                    Text(
                        text = "56",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = "Sunny", fontStyle = FontStyle.Italic,
                    )
                    Text(text = "")
                }
            }
        }
    }
}

@Composable
fun WeatherStateImage(imageCode: String) {
    val image_url = "https://openweathermap.org/img/wn/${imageCode}.png"

    Image(
        painter = rememberAsyncImagePainter(model = image_url),
        contentDescription = "weather image",
        modifier = Modifier.size(80.dp)
    )
}
