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
    suspend fun getTopHeadlines() = apiService.getTopHeadlines("de")
}