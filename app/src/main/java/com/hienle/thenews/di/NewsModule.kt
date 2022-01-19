package com.hienle.thenews.di

import com.hienle.thenews.ui.news.DefaultNewsRemoteDataSource
import com.hienle.thenews.ui.news.DefaultNewsRepository
import com.hienle.thenews.ui.news.NewsRemoteDataSource
import com.hienle.thenews.ui.news.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by Hien Quang Le on 1/19/2022.
 * lequanghien247@gmail.com
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class NewsModule {

    @Binds
    abstract fun bindNewsRemoteDataSource(
        defaultNewsRemoteDataSource: DefaultNewsRemoteDataSource
    ): NewsRemoteDataSource

    @Binds
    abstract fun bindNewsRepository(
        defaultNewsRepository: DefaultNewsRepository
    ): NewsRepository
}