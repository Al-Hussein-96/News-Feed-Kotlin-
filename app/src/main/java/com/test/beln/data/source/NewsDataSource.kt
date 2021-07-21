package com.test.beln.data.source

import androidx.lifecycle.LiveData
import com.test.beln.data.News
import com.test.beln.data.MyResult

interface NewsDataSource {

    fun observeNews(): LiveData<MyResult<List<News>>>

    suspend fun getAllNewsFeed(): MyResult<List<News>>
    suspend fun getNews(newsId: String): MyResult<News>

    suspend fun refreshTasks()
    suspend fun saveNews(news: News)

    suspend fun deleteAllNews()
    suspend fun deleteNewsLazy(newsId: String)







}