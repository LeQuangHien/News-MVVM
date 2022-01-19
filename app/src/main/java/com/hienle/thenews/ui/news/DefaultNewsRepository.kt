package com.hienle.thenews.ui.news

import com.hienle.thenews.api.BaseApiResponse
import com.hienle.thenews.model.ArticleResponse
import com.hienle.thenews.result.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Hien Quang Le on 1/16/2022.
 * lequanghien247@gmail.com
 */

interface NewsRepository {
    suspend fun getTopHeadlines(): Flow<NetworkResult<ArticleResponse>>
}

class DefaultNewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : BaseApiResponse(), NewsRepository {

    override suspend fun getTopHeadlines(): Flow<NetworkResult<ArticleResponse>> {
        return flow {
            emit(safeApiCall { newsRemoteDataSource.getTopHeadlines() })
        }.flowOn(ioDispatcher)
    }
}


