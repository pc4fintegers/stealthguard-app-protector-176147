package com.stealthguard.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stealthguard.data.db.entities.LockRule
import kotlinx.coroutines.flow.Flow

/**
 * PUBLIC_INTERFACE
 * DAO for lock/hide schedule rules.
 */
@Dao
interface LockRuleDao {
    @Query("SELECT * FROM lock_rules ORDER BY triggerAtMillis ASC")
    fun observeAll(): Flow<List<LockRule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rule: LockRule): Long

    @Delete
    suspend fun delete(rule: LockRule)

    @Query("DELETE FROM lock_rules WHERE id = :id")
    suspend fun deleteById(id: Long)
}
