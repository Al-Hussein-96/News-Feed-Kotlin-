package com.test.beln.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.test.beln.data.MyResult
import com.test.beln.data.News
import com.test.beln.data.source.NewsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsLocalDataSource internal constructor(
    private val newsDao: NewsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsDataSource {
    override fun observeNews(): LiveData<MyResult<List<News>>> {
        return newsDao.observeTasks().map {
            MyResult.Success(it)
        }
    }


    override suspend fun getAllNewsFeed(): MyResult<List<News>> = withContext(ioDispatcher) {
        return@withContext try {
            MyResult.Success(newsDao.getNews())
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }

    override suspend fun getNews(newsId: String): MyResult<News> = withContext(ioDispatcher) {
        try {
            val news = newsDao.getNewsById(newsId)
            if (news != null) {
                return@withContext MyResult.Success(news)
            } else {
                return@withContext MyResult.Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext MyResult.Error(e)
        }
    }

    override suspend fun refreshTasks() {
        TODO("Not yet implemented")
    }


    override suspend fun saveNews(news: News) {
        newsDao.insertNews(news)
    }

    override suspend fun deleteAllNews() {
        newsDao.deleteNews()
    }

    override suspend fun deleteNewsLazy(newsId: String) {
        newsDao.deleteNewsByIdLazy(newsId)
    }
}