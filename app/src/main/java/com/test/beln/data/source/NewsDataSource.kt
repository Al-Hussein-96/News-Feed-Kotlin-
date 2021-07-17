package com.test.beln.data.source

import androidx.lifecycle.LiveData
import com.test.beln.data.News
import com.test.beln.data.MyResult

interface NewsDataSource {

    fun observeNews(): LiveData<MyResult<List<News>>>

    suspend fun getNews(): MyResult<List<News>>

    suspend fun refreshTasks()






}