package com.hienle.theschedule.ui.news

import com.hienle.theschedule.api.BaseApiResponse
import com.hienle.theschedule.model.ArticleResponse
import com.hienle.theschedule.result.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by Hien Quang Le on 1/16/2022.
 * lequanghien247@gmail.com
 */

@ActivityRetainedScoped
class NewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : BaseApiResponse() {

    suspend fun getTopHeadlines(): Flow<NetworkResult<ArticleResponse>> {
        return flow {
            emit(safeApiCall { newsRemoteDataSource.getTopHeadlines() })
        }.flowOn(ioDispatcher)
    }

   /* // Mutex to make writes to cached values thread-safe.
    private val latestNewsMutex = Mutex()

    // Cache of the latest news got from the network.
    private var latestNews: List<News> = emptyList()

    suspend fun getLatestNews(refresh: Boolean = false): List<News> {
        if (refresh || latestNews.isEmpty()) {
            val networkResult = newsRemoteDataSource.fetchLatestNews()
            // Thread-safe write to latestNews
            latestNewsMutex.withLock {
                this.latestNews = networkResult
            }
        }

        return latestNewsMutex.withLock { this.latestNews }
    }

    suspend fun getLatestNewsExternalScope(refresh: Boolean = false): List<News> {
        return if (refresh) {
            externalScope.async {
                newsRemoteDataSource.fetchLatestNews().also { networkResult ->
                    // Thread-safe write to latestNews.
                    latestNewsMutex.withLock {
                        latestNews = networkResult
                    }
                }
            }.await()
        } else {
            return latestNewsMutex.withLock { this.latestNews }
        }
    }

    *//**
     * Returns the favorite latest news applying transformations on the flow.
     * These operations are lazy and don't trigger the flow. They just transform
     * the current value emitted by the flow at that point in time.
     *//*
    val favoriteLatestNews: Flow<List<News>> =
        newsRemoteDataSource.latestNews
            .map { news -> // Executes on the default dispatcher
                news.filter { userData.isFavoriteTopic(it) }
            }
            .onEach { news -> // Executes on the default dispatcher
                saveInCache(news)
            }
            // flowOn affects the upstream flow ↑
            .flowOn(defaultDispatcher)
            // the downstream flow ↓ is not affected
            .catch { exception -> // Executes in the consumer's context
                emit(lastCachedNews())
            }


    private fun saveInCache(articleHeadlines: List<News>) {}

    private fun lastCachedNews() :  List<News> {
        return latestNews
    }*/
}


