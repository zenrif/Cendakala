package com.zen.cendakala.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zen.cendakala.data.responses.Response
import com.zen.cendakala.data.responses.Survey
import com.zen.cendakala.data.responses.SurveyUser
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiServices

class SurveyPagingSource(
    private val pref: UserPreference,
    private val apiService: ApiServices,
) : PagingSource<Int, Survey>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Survey> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val token = pref.getUser().token.toString()

            if (token.isNotEmpty()) {
                val responseData = token.let { apiService.purchaseable(it) }
                if (responseData.isSuccessful) {
                    LoadResult.Page(
                        data = responseData.body()?.surveys ?: emptyList(),
                        prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                        nextKey = if (responseData.body()?.surveys.isNullOrEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Error(Exception("Failed load survey"))
                }
            } else {
                LoadResult.Error(Exception("Token empty"))
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Survey>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}

class SurveyRecomPagingSource(
    private val pref: UserPreference,
    private val apiService: ApiServices,
) : PagingSource<Int, Survey>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Survey> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val token = pref.getUser().token.toString()

            if (token.isNotEmpty()) {
                val responseData = token.let { apiService.recommedation(it) }
                if (responseData.isSuccessful) {
                    LoadResult.Page(
                        data = responseData.body()?.surveys ?: emptyList(),
                        prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                        nextKey = if (responseData.body()?.surveys.isNullOrEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Error(Exception("Failed load survey"))
                }
            } else {
                LoadResult.Error(Exception("Token empty"))
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Survey>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}

class SurveyUserPagingSource(
    private val pref: UserPreference,
    private val apiService: ApiServices,
) : PagingSource<Int, SurveyUser>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SurveyUser> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val token = pref.getUser().token.toString()

            if (token.isNotEmpty()) {
                val responseData = token.let { apiService.allSurvey(it) }
                if (responseData.isSuccessful) {
                    LoadResult.Page(
                        data = responseData.body()?.surveys ?: emptyList(),
                        prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                        nextKey = if (responseData.body()?.surveys.isNullOrEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Error(Exception("Failed load survey"))
                }
            } else {
                LoadResult.Error(Exception("Token empty"))
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SurveyUser>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}

class HistoryPagingSource(
    private val pref: UserPreference,
    private val apiService: ApiServices,
) : PagingSource<Int, Response>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Response> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val token = pref.getUser().token.toString()

            if (token.isNotEmpty()) {
                val responseData = token.let { apiService.history(it) }
                if (responseData.isSuccessful) {
                    LoadResult.Page(
                        data = responseData.body()?.responses ?: emptyList(),
                        prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                        nextKey = if (responseData.body()?.responses.isNullOrEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Error(Exception("Failed load survey"))
                }
            } else {
                LoadResult.Error(Exception("Token empty"))
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Response>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}