package com.zen.cendakala.data.repositories

import com.zen.cendakala.data.source.local.CreateSurvey
import com.zen.cendakala.data.source.local.CreateSurveyDao

class CreateSurveyRepository(private val createSurveyDao: CreateSurveyDao){

    suspend fun insert(createSurvey: CreateSurvey) {
        createSurveyDao.insert(createSurvey)
    }

    suspend fun getAll(): List<CreateSurvey> {
        return createSurveyDao.getAll()
    }

    suspend fun getById(id: Int): CreateSurvey {
        return createSurveyDao.getById(id)
    }

    suspend fun update(createSurvey: CreateSurvey) {
        createSurveyDao.update(createSurvey)
    }

    suspend fun deleteById(id: Int) {
        createSurveyDao.deleteById(id)
    }
}