package com.hienle.thenews.test.util.fakes

import com.hienle.thenews.model.Article
import com.hienle.thenews.model.ArticleResponse
import com.hienle.thenews.model.Source
import com.hienle.thenews.result.NetworkResult
import com.hienle.thenews.ui.news.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Hien Quang Le on 1/19/2022.
 * lequanghien247@gmail.com
 */

class FakeNewsRepository() : NewsRepository {

    override suspend fun getTopHeadlines(): Flow<NetworkResult<ArticleResponse>> {
        return flow {
            val articleResponse = ArticleResponse(
                status = "ok", totalResults = 34,
                listOf(
                    Article(
                        Source(id = "Test id", name = "Test name"),
                        author = "Test Author",
                        description = "Test Description",
                        title = "Test title",
                        url = "url",
                        urlToImage = "urlToImage",
                        publishedAt = "today",
                        content = "Test content",
                    )
                )
            )
            NetworkResult.Success<ArticleResponse>(articleResponse)
        }
    }
}

class FakeNewsRepositoryError() : NewsRepository {

    override suspend fun getTopHeadlines(): Flow<NetworkResult<ArticleResponse>> {
        return flow {
            NetworkResult.Error<ArticleResponse>("Error", null)
        }
    }
}