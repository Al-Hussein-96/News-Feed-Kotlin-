package com.test.beln.data.source

import androidx.lifecycle.LiveData
import com.test.beln.data.News
import com.test.beln.data.MyResult
import kotlinx.coroutines.*

class DefaultNewsRepository(
    val newsRemoteDataSource: NewsDataSource,
    val newsLocalDataSource: NewsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : NewsRepository {

    override suspend fun getNews(forceUpdate: Boolean): MyResult<List<News>> {
        if (forceUpdate) {
            newsRemoteDataSource.getNews()
        }
        return newsRemoteDataSource.getNews()

    }

    override fun observeNews(): LiveData<MyResult<List<News>>> {
        return newsLocalDataSource.observeNews()
    }

    override suspend fun refreshNews() {
        updateNewsFromRemoteDataSource()
    }

    private suspend fun updateNewsFromRemoteDataSource() {
//        val remoteTasks = newsRemoteDataSource.getNews()
//
//        if (remoteTasks is MyResult.Success) {
//            // Real apps might want to do a proper sync, deleting, modifying or adding each task.
//            newsLocalDataSource.deleteAllTasks()
//            remoteTasks.data.forEach { task ->
//                newsLocalDataSource.saveTask(task)
//            }
//        } else if (remoteTasks is MyResult.Error) {
//            throw remoteTasks.exception
//        }
    }

    override suspend fun deleteAllNews() {
//        withContext(ioDispatcher) {
//            coroutineScope {
//                launch { newsRemoteDataSource.deleteAllNews() }
//            }
//        }
    }

}