package com.lacalera.testapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lacalera.testapp.data.local.model.CharacterEntity

@Dao
interface CharacterDao : BaseDao<CharacterEntity> {
    @Query("SELECT * FROM ${CharacterEntity.TABLE_NAME} WHERE flag = ${CharacterEntity.OPERATING}")
    suspend fun getTaskPending(): List<CharacterEntity>

    @Insert(
        onConflict = androidx.room.OnConflictStrategy.REPLACE
    )
    suspend fun save(characterEntity: CharacterEntity): Long


}
