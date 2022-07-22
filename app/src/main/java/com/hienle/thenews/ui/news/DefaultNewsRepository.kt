package com.hienle.thenews.ui.news

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.hienle.thenews.model.ArticleResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Hien Quang Le on 1/16/2022.
 * lequanghien247@gmail.com
 */

interface NewsRepository {
    suspend fun getTopHeadlines(): Either<CallError, ArticleResponse>
}

class DefaultNewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : NewsRepository {

    override suspend fun getTopHeadlines(): Either<CallError, ArticleResponse> =
        withContext(ioDispatcher) {
            newsRemoteDataSource.getTopHeadlines()
        }

}


