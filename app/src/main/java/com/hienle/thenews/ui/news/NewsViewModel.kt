package com.hienle.thenews.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hienle.thenews.ui.state.Message
import com.hienle.thenews.ui.state.NewsItemUiState
import com.hienle.thenews.ui.state.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var fetchJob: Job? = null

    private val _uiState = MutableStateFlow(NewsUiState(isFetchingArticles = true))
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    fun getTopHeadlines() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = newsRepository.getTopHeadlines()
            result.fold(
                ifLeft = {
                    _uiState.update {
                        val messages = listOf(Message(1, "Error getting headlines"))
                        it.copy(userMessages = messages, isFetchingArticles = false)
                    }
                },
                ifRight = { articleResponse ->
                    _uiState.update {
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
        }
    }

    fun userMessageShown(messageId: Long) {
        _uiState.update { currentUiState ->
            val messages = currentUiState.userMessages.filterNot { it.id == messageId }
            currentUiState.copy(userMessages = messages)
        }
    }
}