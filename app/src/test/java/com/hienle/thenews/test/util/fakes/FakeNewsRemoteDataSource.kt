package com.hienle.thenews.test.util.fakes

import com.hienle.thenews.model.Article
import com.hienle.thenews.model.ArticleResponse
import com.hienle.thenews.model.Source
import com.hienle.thenews.ui.news.NewsRemoteDataSource
import retrofit2.Response

/**
 * Created by Hien Quang Le on 1/19/2022.
 * lequanghien247@gmail.com
 */

class FakeNewsRemoteDataSource() : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(): Response<ArticleResponse> {
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
        return Response.success(articleResponse)
    }
}