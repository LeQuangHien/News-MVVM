package com.hienle.thenews.ui.news

import com.hienle.thenews.model.ArticleResponse
import com.hienle.thenews.test.util.MainCoroutineRule
import com.hienle.thenews.test.util.fakes.FakeNewsRemoteDataSource
import com.hienle.thenews.test.util.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Hien Quang Le on 1/19/2022.
 * lequanghien247@gmail.com
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest {

    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Test
    fun testGetTopHeadlines_success() = coroutineRule.runBlockingTest {
        val newsRemoteDataSource = FakeNewsRemoteDataSource()
        val newsRepository =
            DefaultNewsRepository(newsRemoteDataSource, coroutineRule.testDispatcher)
        val headlines: ArticleResponse? = newsRepository.getTopHeadlines().orNull()
        val dataSourceHeadline: ArticleResponse? = newsRemoteDataSource.getTopHeadlines().orNull()
        assert(headlines == dataSourceHeadline)
    }
}