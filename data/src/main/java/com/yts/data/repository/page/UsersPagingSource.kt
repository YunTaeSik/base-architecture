package com.yts.data.repository.page

import androidx.paging.PagingSource
import com.yts.data.response.SearchResponse
import com.yts.data.source.remote.SearchService
import com.yts.domain.entity.User

class UsersPagingSource(
    private val searchService: SearchService,
    private val token: String,
    private val q: String,
    private val per_page: Int,
    private var page: Int
) :
    PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {
            page = if (params is LoadParams.Append) params.key else 1
            val searchResponse = searchService.getUsers(
                token, q, per_page, page
            )
            val items = searchResponse.items
            page++
            return LoadResult.Page(
                data = items!!,
                prevKey = null,
                nextKey = nextKey(searchResponse)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    private fun nextKey(searchResponse: SearchResponse): Int? {
        return if (per_page * page < searchResponse.total_count) {
            page
        } else {
            null
        }
    }

}