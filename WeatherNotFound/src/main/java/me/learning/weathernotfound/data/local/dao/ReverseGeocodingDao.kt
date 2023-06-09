package me.learning.weathernotfound.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.learning.weathernotfound.domain.reverseGeocoding.databaseModels.ReverseGeocodingEntity

@Dao
internal interface ReverseGeocodingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReverseGeocodingEntities(reverseGeocodingEntities: List<ReverseGeocodingEntity>): List<Long>

    @Delete
    suspend fun deleteReverseGeocodingEntities(reverseGeocodingEntity: List<ReverseGeocodingEntity>)

    @Update
    suspend fun updateReverseGeocodingEntity(reverseGeocodingEntity: ReverseGeocodingEntity)

    @Query("SELECT * FROM tbl_reverse_geocoding WHERE latitude =:latitude AND longitude =:longitude")
    suspend fun getReverseGeocodingByCoordinates(
        latitude: Double,
        longitude: Double
    ): List<ReverseGeocodingEntity>

    @Query("DELETE FROM tbl_reverse_geocoding WHERE updated_at<=:selectedTimeStamp")
    suspend fun deleteReverseGeocodingEntitiesOlderThan(selectedTimeStamp: Long)

    @Query("DELETE FROM tbl_reverse_geocoding")
    suspend fun invalidateCache()
}