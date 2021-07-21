package com.test.beln.data.source

import androidx.lifecycle.LiveData
import com.test.beln.data.News
import com.test.beln.data.MyResult
import kotlinx.coroutines.*
import timber.log.Timber

class DefaultNewsRepository(
    val newsRemoteDataSource: NewsDataSource,
    val newsLocalDataSource: NewsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : NewsRepository {

    override suspend fun getNews(forceUpdate: Boolean): MyResult<List<News>> {
        Timber.tag("Mohammad").i("getNews DefaultNewsRepository")
        if (forceUpdate) {
            newsRemoteDataSource.getAllNewsFeed()
        }
        return newsRemoteDataSource.getAllNewsFeed()

    }

    override fun observeNews(): LiveData<MyResult<List<News>>> {
        return newsLocalDataSource.observeNews()
    }

    override suspend fun refreshNews() {
        updateNewsFromRemoteDataSource()
    }

    private suspend fun updateNewsFromRemoteDataSource() {
        val remoteNews = newsRemoteDataSource.getAllNewsFeed()

        if (remoteNews is MyResult.Success) {
            // Real apps might want to do a proper sync, deleting, modifying or adding each task.
            newsLocalDataSource.deleteAllNews()
            remoteNews.data.forEach { news ->
                newsLocalDataSource.saveNews(news)
            }
        } else if (remoteNews is MyResult.Error) {
            throw remoteNews.exception
        }
    }

    override suspend fun deleteAllNews() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { newsRemoteDataSource.deleteAllNews() }
            }
        }
    }

}