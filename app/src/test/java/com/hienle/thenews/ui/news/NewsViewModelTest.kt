package com.hienle.thenews.ui.news

import arrow.retrofit.adapter.either.networkhandling.HttpError
import com.hienle.thenews.test.util.MainCoroutineRule
import com.hienle.thenews.test.util.fakes.FakeNewsRepository
import com.hienle.thenews.test.util.fakes.FakeNewsRepositoryError
import com.hienle.thenews.test.util.runBlockingTest
import com.hienle.thenews.ui.state.Message
import com.hienle.thenews.ui.state.NewsItemUiState
import com.hienle.thenews.ui.state.NewsUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Hien Quang Le on 1/19/2022.
 * lequanghien247@gmail.com
 */

/**
 * Unit tests for [NewsViewModel]
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun testData_success() = coroutineRule.runBlockingTest {
        val newsRepository =
            FakeNewsRepository()
        val newsViewModel = NewsViewModel(newsRepository)

        val repositoryUiState = MutableStateFlow(NewsUiState(isFetchingArticles = true))
        newsViewModel.getTopHeadlines()

        newsRepository.getTopHeadlines().fold(
            ifLeft = {},
            ifRight = { articleResponse ->
                repositoryUiState.update {
                    val items: List<NewsItemUiState> =
                        articleResponse.articles.map { article ->
                            NewsItemUiState(
                                source = article.source,
                                author = article.author,
                                title = article.title,
                                description = article.description,
                                url = article.url,
                                urlToImage = article.urlToImage,
                                publishedAt = article.publishedAt,
                                content = article.content
                            )
                        }

                    it.copy(newsItems = items, isFetchingArticles = false)
                }
            }
        )

        assert(newsViewModel.uiState.value == repositoryUiState.value)
    }

    @Test
    fun testData_error() = coroutineRule.runBlockingTest {
        val newsRepository =
            FakeNewsRepositoryError()
        val newsViewModel = NewsViewModel(newsRepository)

        val repositoryUiState = MutableStateFlow(NewsUiState(isFetchingArticles = true))
        newsViewModel.getTopHeadlines()

        newsRepository.getTopHeadlines().fold(
            ifLeft = { callError ->
                repositoryUiState.update {
                    val messages = listOf(Message(1, (callError as HttpError).message))
                    it.copy(userMessages = messages, isFetchingArticles = false)
                }
            },
            ifRight = {}
        )

        assert(newsViewModel.uiState.value == repositoryUiState.value)
    }

}

