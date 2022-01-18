package com.hienle.thenews.ui.state

import com.hienle.thenews.model.Source

/**
 * Created by Hien Quang Le on 1/18/2022.
 * lequanghien247@gmail.com
 */

data class NewsUiState(
    val isFavorite: Boolean = false,
    val isFetchingArticles: Boolean = false,
    val isSignedIn: Boolean = false,
    val isPremium: Boolean = false,
    val newsItems: List<NewsItemUiState> = listOf(),
    val userMessages: List<Message> = listOf()
)

data class Message(val id: Long, val message: String)

data class NewsItemUiState(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)