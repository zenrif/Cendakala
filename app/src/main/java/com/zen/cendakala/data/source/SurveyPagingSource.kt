package com.zen.cendakala.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zen.cendakala.data.responses.Survey
import com.zen.cendakala.data.source.local.UserPreference
import com.zen.cendakala.data.source.remote.ApiServices

class SurveyPagingSource(
    private val pref: UserPreference,
    private val apiService: ApiServices
) : PagingSource<Int, Survey>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Survey> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val token = pref.getUser().token.toString()

            if (token.isNotEmpty()) {
                val responseData = token.let { apiService.surveis("Bearer $it", page, params.loadSize, 0) }
                if (responseData.isSuccessful) {
                    LoadResult.Page(
                        data = responseData.body()?.listSurvey ?: emptyList(),
                        prevKey = if (page == INITIAL_PAGE_INDEX) null else page -1,
                        nextKey = if (responseData.body()?.listSurvey.isNullOrEmpty()) null else page + 1
                    )
                } else {
                    LoadResult.Error(Exception("Failed load story"))
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