package com.hienle.thenews.ui.news

import com.hienle.thenews.ui.state.Message

/**
 * Created by Hien Quang Le on 1/16/2022.
 * lequanghien247@gmail.com
 */

// Models the UI state for the Latest news screen.
data class LatestNewsUiState(
    val news: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val userMessages: List<Message> = emptyList()
)

data class News(
    val title: String,
    val body: String,
    val bookmarked: Boolean = false,
    val publicationDate: String
)

data class FavoriteArticleUiState(
    val isFavorite: Boolean = false,
    val articleHeadline: List<ArticleHeadline> = listOf()
)

data class ArticleHeadline(
    val title: String,
    val body: String,
    val bookmarked: Boolean = false,
    val publicationDate: String
)

