package me.learning.weathernotfound.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.learning.weathernotfound.domain.reverseGeocoding.databaseModels.ReverseGeocodingEntity

/**
 * Provide everything you need to access and operate on ReverseGeocoding information
 */
@Dao
internal interface ReverseGeocodingDao {

    /**
     * Insert list of [ReverseGeocodingEntity] models into the database.
     * NOTE: if the given [ReverseGeocodingEntity] is duplicate, it will replaced!
     * @return a list of auto-generated ids from Room database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReverseGeocodingEntities(reverseGeocodingEntities: List<ReverseGeocodingEntity>): List<Long>

    /**
     * Delete all given [ReverseGeocodingEntity] models.
     */
    @Delete
    suspend fun deleteReverseGeocodingEntities(reverseGeocodingEntity: List<ReverseGeocodingEntity>)

    /**
     * Update given [ReverseGeocodingEntity] model into the database.
     * NOTE: if operation failed for any reason, nothing will change!
     */
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateReverseGeocodingEntity(reverseGeocodingEntity: ReverseGeocodingEntity)

    /**
     * Search for given coordinates into the database.
     * @return if any information exist for that coordinates, it will return as a list. otherwise
     * the list will be empty!
     * NOTE: remember we can have multiple [ReverseGeocodingEntity] objects for a coordinates!
     */
    @Query("SELECT * FROM tbl_reverse_geocoding WHERE latitude =:latitude AND longitude =:longitude")
    suspend fun getReverseGeocodingByCoordinates(
        latitude: Double,
        longitude: Double
    ): List<ReverseGeocodingEntity>

    /**
     * Delete all [ReverseGeocodingEntity] models which they are older than [selectedTimeStamp].
     */
    @Query("DELETE FROM tbl_reverse_geocoding WHERE updated_at<=:selectedTimeStamp")
    suspend fun deleteReverseGeocodingEntitiesOlderThan(selectedTimeStamp: Long)

    /**
     * Delete everything exist on [ReverseGeocodingEntity] table.
     */
    @Query("DELETE FROM tbl_reverse_geocoding")
    suspend fun invalidateCache()
}