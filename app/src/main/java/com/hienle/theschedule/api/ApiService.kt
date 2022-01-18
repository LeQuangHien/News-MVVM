package com.hienle.theschedule.api

import androidx.lifecycle.LiveData
import com.hienle.theschedule.model.ArticleResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Hien Quang Le on 1/18/2022.
 * lequanghien247@gmail.com
 */

interface ApiService {
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): Response<ArticleResponse>
}