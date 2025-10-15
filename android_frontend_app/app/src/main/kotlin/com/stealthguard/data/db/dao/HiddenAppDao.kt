package com.stealthguard.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stealthguard.data.db.entities.HiddenApp
import kotlinx.coroutines.flow.Flow

/**
 * PUBLIC_INTERFACE
 * DAO for hidden apps.
 */
@Dao
interface HiddenAppDao {
    @Query("SELECT * FROM hidden_apps")
    fun observeAll(): Flow<List<HiddenApp>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HiddenApp)

    @Delete
    suspend fun delete(entity: HiddenApp)

    @Query("SELECT EXISTS(SELECT 1 FROM hidden_apps WHERE packageName = :pkg)")
    suspend fun isHidden(pkg: String): Boolean
}
