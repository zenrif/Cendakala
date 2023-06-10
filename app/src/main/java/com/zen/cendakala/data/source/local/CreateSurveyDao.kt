package com.zen.cendakala.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface CreateSurveyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(createSurvey: CreateSurvey)

    @Query("SELECT * FROM create_survey")
    suspend fun getAll(): List<CreateSurvey>

    @Query("SELECT * FROM create_survey WHERE id = :id")
    suspend fun getById(id: Int): CreateSurvey

    @Query("DELETE FROM create_survey WHERE id = :id")
    suspend fun deleteById(id: Int)
    @Update()
    suspend fun update(createSurvey: CreateSurvey)

}
