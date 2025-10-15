package com.stealthguard.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stealthguard.data.db.entities.LockedApp
import kotlinx.coroutines.flow.Flow

/**
 * PUBLIC_INTERFACE
 * DAO for locked apps.
 */
@Dao
interface LockedAppDao {
    @Query("SELECT * FROM locked_apps")
    fun observeAll(): Flow<List<LockedApp>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: LockedApp)

    @Delete
    suspend fun delete(entity: LockedApp)

    @Query("SELECT EXISTS(SELECT 1 FROM locked_apps WHERE packageName = :pkg)")
    suspend fun isLocked(pkg: String): Boolean
}
