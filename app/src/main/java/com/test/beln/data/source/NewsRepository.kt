package com.test.beln.data.source

import androidx.lifecycle.LiveData
import com.test.beln.data.News
import com.test.beln.data.MyResult


interface NewsRepository {

    fun observeNews(): LiveData<MyResult<List<News>>>

    suspend fun refreshNews()

    suspend fun getNews(forceUpdate: Boolean = false): MyResult<List<News>>


    suspend fun deleteAllNews()




}