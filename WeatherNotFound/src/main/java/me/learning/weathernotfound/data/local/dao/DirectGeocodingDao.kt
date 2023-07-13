package me.learning.weathernotfound.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.learning.weathernotfound.domain.directGeocoding.databaseModels.DirectGeocodingEntity

@Dao
internal interface DirectGeocodingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDirectGeocodingEntities(directGeocodingEntities: List<DirectGeocodingEntity>): List<Long>

    @Delete
    suspend fun deleteDirectGeocodingEntities(directGeocodingEntities: List<DirectGeocodingEntity>)

    @Update
    suspend fun updateDirectGeocodingEntity(directGeocodingEntity: DirectGeocodingEntity)

    @Query("SELECT * FROM tbl_direct_geocoding WHERE coordinate_name LIKE :coordinateName")
    suspend fun getDirectGeocodingByCoordinateName(coordinateName: String): List<DirectGeocodingEntity>

    @Query("DELETE FROM tbl_direct_geocoding WHERE updated_at<=:selectedTimeStamp")
    suspend fun deleteDirectGeocodingEntitiesOlderThan(selectedTimeStamp: Long)

    @Query("DELETE FROM tbl_direct_geocoding")
    suspend fun invalidateCache()
}