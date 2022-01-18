package com.hienle.thenews.ui.news

import com.hienle.thenews.api.ApiService
import javax.inject.Inject

/**
 * Created by Hien Quang Le on 1/16/2022.
 * lequanghien247@gmail.com
 */

class NewsRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    /*val latestNews: Flow<List<News>> = flow {
        while(true) {
            val latestNews = newsApi.fetchLatestNews()
            emit(latestNews) // Emits the result of the request to the flow
            delay(refreshIntervalMs) // Suspends the coroutine for some time
        }
    }.flowOn(ioDispatcher)*/


    suspend fun getTopHeadlines() = apiService.getTopHeadlines("de")
}