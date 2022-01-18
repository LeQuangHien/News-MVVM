package com.hienle.thenews.model

/**
 * Created by Hien Quang Le on 1/18/2022.
 * lequanghien247@gmail.com
 */

data class ArticleResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {
}