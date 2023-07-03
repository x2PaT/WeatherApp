package com.example.weatherapp.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.favorites.FavoritesScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWidget(
    city: String = "City",
    country: String = "Country",
    title: String,
    navController: NavController,
    isMainScreen: Boolean = true,
    favoritesScreenViewModel: FavoritesScreenViewModel = hiltViewModel(),
    elevation: Dp = 0.dp,
    onSearchActionActionClicked: () -> Unit = {},
    onLeadingButtonClicked: (() -> Unit)? = null,
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
//    val showIt = remember {
//        mutableStateOf(false)
//    }
    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingsDropDownMenu(showDialog = showDialog, navController = navController)

    }

    Surface(
        shadowElevation = elevation,
        modifier = Modifier.padding(elevation),
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.Transparent
            ),
            title = {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                )
            },
            actions = {
                if (isMainScreen) {
                    IconButton(onClick = { onSearchActionActionClicked() }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search icon",
                        )
                    }
                    IconButton(onClick = {
                        showDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More icon",
                        )
                    }
                }
            },
            navigationIcon = {
                if (isMainScreen) {
                    val isFavorite =
                        favoritesScreenViewModel.favoritesList.collectAsState().value.any {
                            it.city == city
                        }

                    IconButton(
                        onClick = {
                            if (!isFavorite) {
                                favoritesScreenViewModel.addFavorite(
                                    favorite = Favorite(
                                        city = city,
                                        country = country,
                                    )
                                )
                                showToast(context = context, "Added to favorites")
                            } else {
                                favoritesScreenViewModel.removeFavorite(
                                    favorite = Favorite(
                                        city = city,
                                        country = country,
                                    )
                                )
                                showToast(context = context, "Removed from favorites")

                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (!isFavorite) {
                                Icons.Default.FavoriteBorder
                            } else {
                                Icons.Default.Favorite
                            },
                            tint = Color.Red,
                            contentDescription = "fav icon",
                        )
                    }
                } else if (onLeadingButtonClicked != null) {
                    IconButton(onClick = onLeadingButtonClicked) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "nav icon",
                        )
                    }
                }
            },
        )
    }
}

// doesn't make sense to use here showit
//it was mutablestateof<boolean>

fun showToast(context: Context, text: String, showIt: Boolean = true) {
//    if (showIt.value) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
//    }
}

@Composable
fun ShowSettingsDropDownMenu(
    showDialog: MutableState<Boolean>, navController: NavController
) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val dropDownItems = listOf(
        "About",
        "Favorites",
        "Settings",
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
            .background(Color.White)
    ) {
        DropdownMenu(modifier = Modifier.width(140.dp), expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            dropDownItems.forEach {
                DropdownMenuItem(text = { Text(text = it) }, leadingIcon = {
                    Icon(
                        imageVector = when (it) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.Favorite
                            else -> Icons.Default.Settings
                        }, contentDescription = when (it) {
                            "About" -> "About item icon"
                            "Favorites" -> "Favorites item icon"
                            "Settings" -> "Settings item icon"
                            else -> "Settings icon"
                        }
                    )
                }, onClick = {
                    navController.navigate(
                        when (it) {
                            "About" -> WeatherScreens.AboutScreen.name
                            "Favorites" -> WeatherScreens.FavoritesScreen.name
                            else -> WeatherScreens.SettingsScreen.name
                        }
                    )
                    expanded = false
                    showDialog.value = false
                })
            }
        }
    }
}