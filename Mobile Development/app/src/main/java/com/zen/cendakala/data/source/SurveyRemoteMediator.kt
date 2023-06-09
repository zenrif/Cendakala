package com.zen.cendakala.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zen.cendakala.data.responses.Response
import com.zen.cendakala.data.responses.Survey
import com.zen.cendakala.data.responses.SurveyUser
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiServices

@OptIn(ExperimentalPagingApi::class)
class SurveyRemoteMediator(
    private val pref: UserPreference,
    private val apiService: ApiServices,
) : RemoteMediator<Int, Survey>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Survey>,
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        val token = pref.getUser().token.toString()

        try {
            val responseData = token.let {
                apiService.purchaseable(
                    it,
                )
            }

            return if (responseData.isSuccessful) {
                val endOfPaginationReached = responseData.body()!!.surveys.isEmpty()
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Exception("Failed load survey"))
            }
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}

@OptIn(ExperimentalPagingApi::class)
class SurveyRecomRemoteMediator(
    private val pref: UserPreference,
    private val apiService: ApiServices,
) : RemoteMediator<Int, Survey>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Survey>,
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        val token = pref.getUser().token.toString()

        try {
            val responseData = token.let {
                apiService.recommedation(
                    it,
                )
            }

            return if (responseData.isSuccessful) {
                val endOfPaginationReached = responseData.body()!!.surveys.isEmpty()
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Exception("Failed load survey"))
            }
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}

@OptIn(ExperimentalPagingApi::class)
class SurveyUserRemoteMediator(
    private val pref: UserPreference,
    private val apiService: ApiServices,
) : RemoteMediator<Int, SurveyUser>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SurveyUser>,
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        val token = pref.getUser().token.toString()

        try {
            val responseData = token.let {
                apiService.allSurvey(
                    it,
                )
            }

            return if (responseData.isSuccessful) {
                val endOfPaginationReached = responseData.body()!!.surveys.isEmpty()
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Exception("Failed load survey"))
            }
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}

@OptIn(ExperimentalPagingApi::class)
class HistoryRemoteMediator(
    private val pref: UserPreference,
    private val apiService: ApiServices,
) : RemoteMediator<Int, Response>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Response>,
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        val token = pref.getUser().token.toString()

        try {
            val responseData = token.let {
                apiService.history(
                    it,
                )
            }

            return if (responseData.isSuccessful) {
                val endOfPaginationReached = responseData.body()!!.responses.isEmpty()
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Exception("Failed load survey"))
            }
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}