package me.learning.weathernotfound.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.learning.weathernotfound.domain.directGeocoding.databaseModels.DirectGeocodingEntity

/**
 * Provide everything you need to access and operate on DirectGeocoding information
 */
@Dao
internal interface DirectGeocodingDao {

    /**
     * Insert list of [DirectGeocodingEntity] models into the database.
     * NOTE: if the given [DirectGeocodingEntity] is duplicate, it will replaced!
     * @return a list of auto-generated ids from Room database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDirectGeocodingEntities(directGeocodingEntities: List<DirectGeocodingEntity>): List<Long>

    /**
     * Delete all given [DirectGeocodingEntity] models.
     */
    @Delete
    suspend fun deleteDirectGeocodingEntities(directGeocodingEntities: List<DirectGeocodingEntity>)

    /**
     * Update given [DirectGeocodingEntity] model into the database.
     * NOTE: if operation failed for any reason, nothing will change!
     */
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateDirectGeocodingEntity(directGeocodingEntity: DirectGeocodingEntity)

    /**
     * Search for given coordinate name into the database.
     * @return if any information exist for that coordinate name, it will return as a list. otherwise
     * the list will be empty!
     * NOTE: remember we can have multiple [DirectGeocodingEntity] objects for a coordinate name!
     */
    @Query("SELECT * FROM tbl_direct_geocoding WHERE coordinate_name LIKE :coordinateName")
    suspend fun getDirectGeocodingByCoordinateName(coordinateName: String): List<DirectGeocodingEntity>

    /**
     * Delete all [DirectGeocodingEntity] models which they are older than [selectedTimeStamp].
     */
    @Query("DELETE FROM tbl_direct_geocoding WHERE updated_at<=:selectedTimeStamp")
    suspend fun deleteDirectGeocodingEntitiesOlderThan(selectedTimeStamp: Long)

    /**
     * Delete everything exist on [DirectGeocodingEntity] table.
     */
    @Query("DELETE FROM tbl_direct_geocoding")
    suspend fun invalidateCache()
}