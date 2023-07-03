package com.example.weatherapp.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {
    private val _favoritesList = MutableStateFlow<List<Favorite>>(emptyList())
    val favoritesList = _favoritesList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.getAllFavorites().distinctUntilChanged().collect { listOfFav ->
                if (listOfFav.isEmpty()) {
                    // check
                }
                _favoritesList.value = listOfFav
            }
        }
    }

    //
    //Doesn't update state when added fav
    //    fun isFavorite(city: String): Boolean {
    //
    //        val list = favoritesList.value.filter {
    //            it.city == city
    //        }
    //        return list.isNotEmpty()
    //    }
    //
    fun addFavorite(favorite: Favorite) =
        viewModelScope.launch { favoritesRepository.createFavorites(favorite) }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch { favoritesRepository.updateFavorites(favorite) }

    fun removeFavorite(favorite: Favorite) =
        viewModelScope.launch { favoritesRepository.deleteFavorites(favorite) }

    fun deleteAllFavorites() =
        viewModelScope.launch { favoritesRepository.deleteAllFavorites() }

}