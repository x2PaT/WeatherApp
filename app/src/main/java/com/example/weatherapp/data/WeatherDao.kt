package com.example.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query(value = "SELECT * from  fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query(value = "SELECT * from  fav_tbl where city =:city")
    suspend fun getFavByID(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAll()

}

