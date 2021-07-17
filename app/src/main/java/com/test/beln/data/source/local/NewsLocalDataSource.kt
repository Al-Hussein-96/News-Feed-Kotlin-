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


    override suspend fun getNews(): MyResult<List<News>> = withContext(ioDispatcher) {
        return@withContext try {
            MyResult.Success(newsDao.getNews())
        } catch (e: Exception) {
            MyResult.Error(e)
        }
    }

    override suspend fun refreshTasks() {
        TODO("Not yet implemented")
    }
}