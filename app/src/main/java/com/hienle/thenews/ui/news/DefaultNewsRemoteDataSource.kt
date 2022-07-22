package com.hienle.thenews.ui.news

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.hienle.thenews.api.ApiService
import com.hienle.thenews.model.ArticleResponse
import javax.inject.Inject

/**
 * Created by Hien Quang Le on 1/16/2022.
 * lequanghien247@gmail.com
 */
interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(): Either<CallError, ArticleResponse>
}

class DefaultNewsRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : NewsRemoteDataSource {

    override suspend fun getTopHeadlines() = apiService.getTopHeadlines("de")
}