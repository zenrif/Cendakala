package com.zen.cendakala.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zen.cendakala.data.responses.Survey
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiServices

@OptIn(ExperimentalPagingApi::class)
class SurveyRemoteMediator(
    private val pref: UserPreference,
    private val apiService: ApiServices
) : RemoteMediator<Int, Survey>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Survey>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        val token = pref.getUser().token.toString()

        try {
            val responseData = token.let { apiService.purchaseable(
                it,
            ) }

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