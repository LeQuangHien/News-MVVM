package com.hienle.theschedule.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hienle.theschedule.result.NetworkResult
import com.hienle.theschedule.ui.state.Message
import com.hienle.theschedule.ui.state.NewsItemUiState
import com.hienle.theschedule.ui.state.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var fetchJob: Job? = null

    private val _uiState = MutableStateFlow(NewsUiState(isFetchingArticles = true))
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _favoriteUiState = MutableStateFlow(NewsUiState())
    val favoriteUiState: StateFlow<NewsUiState> = _favoriteUiState.asStateFlow()


   /* init {
        viewModelScope.launch {
            newsRepository.favoriteLatestNews
                // Intermediate catch operator. If an exception is thrown,
                // catch and update the UI
                .catch { exception -> notifyError(exception) }
                .collect { favoriteNews ->
                    // Update View with the latest favorite news
                    _favoriteUiState.update { currentFavoriteUiState ->
                        currentFavoriteUiState.copy(
                            isFavorite = true,
                            newsItems = convertToUiState(favoriteNews)
                        )
                    }
                }
        }
    }

    fun refreshNews(isOnline: Boolean) {
        viewModelScope.launch {
            // If there isn't internet connection, show a new message on the screen.
            if (!isOnline) {
                _uiState.update { currentUiState ->
                    val messages = currentUiState.userMessages + Message(
                        id = UUID.randomUUID().mostSignificantBits,
                        message = "No Internet connection"
                    )
                    currentUiState.copy(userMessages = messages)
                }
                return@launch
            } else {
                try {
                    val newsItems = newsRepository.getLatestNews(refresh = true)
                    _uiState.update {
                        it.copy(
                            newsItems = convertToUiState(newsItems)
                        )
                    }
                } catch (ioe: IOException) {
                    // Handle the error and notify the notify the UI when appropriate.
                    _uiState.update {
                        val messages = listOf<Message>(Message(1, ioe.message.toString()))
                        it.copy(userMessages = messages)
                    }
                }
            }
        }
    }
*/
    /* val newsListUiItems = newsRepository.latestNews.map { news ->
         NewsItemUiState(
             title = news.title,
             body = news.body,
             bookmarked = news.bookmarked,
             publicationDate = news.publicationDate,
             // Business logic is passed as a lambda function that the
             // UI calls on click events.
             onBookmark = {
                 newsRepository.addBookmark(news.id)
             }
         )
     }*/


    fun getTopHeadlines() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            newsRepository.getTopHeadlines().collect { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        // bind data to the view
                        if (response.data != null) {
                            _uiState.update {
                                val items: List<NewsItemUiState> = response.data.articles.map { article ->
                                    NewsItemUiState( source = article.source,
                                    author = article.author,
                                    title = article.title,
                                    description = article.description,
                                    url = article.url,
                                    urlToImage = article.urlToImage,
                                    publishedAt = article.publishedAt,
                                    content = article.content) }
                                it.copy(newsItems = items, isFetchingArticles = false)
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update {
                            val messages = listOf(Message(1, response.message.toString()))
                            it.copy(userMessages = messages, isFetchingArticles = false)
                        }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update {
                            it.copy(isFetchingArticles = true)
                        }
                    }
                }
            }
        }
    }

    fun userMessageShown(messageId: Long) {
        _uiState.update { currentUiState ->
            val messages = currentUiState.userMessages.filterNot { it.id == messageId }
            currentUiState.copy(userMessages = messages)
        }
    }

    private fun notifyError(exception: Throwable) {}

}