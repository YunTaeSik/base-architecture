package com.yts.data.source.remote

import com.yts.data.response.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * 레트로핏2를 사용하였음
 * Restful api 모든 자원을 URI
 * Post생성 get조회 put수정 delete삭제
 */
interface SearchService {

    @GET("/search/users")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("q") q: String,
        @Query("per_page") per_page: Int?,
        @Query("page") page: Int?
    ): SearchResponse
}