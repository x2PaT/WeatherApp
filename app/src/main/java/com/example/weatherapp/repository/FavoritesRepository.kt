package com.example.weatherapp.repository

import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val weatherDao: WeatherDao) {
    suspend fun createFavorites(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun deleteFavorites(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun updateFavorites(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAll()

    suspend fun getFavoritesByID(city: String) = weatherDao.getFavByID(city)

    fun getAllFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    //.flowOn(Dispatchers.IO).conflate()
}