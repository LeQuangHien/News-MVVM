package com.hienle.thenews.model

/**
 * Created by Hien Quang Le on 1/18/2022.
 * lequanghien247@gmail.com
 */

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)