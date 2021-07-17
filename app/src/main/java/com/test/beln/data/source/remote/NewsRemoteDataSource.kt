package com.test.beln.data.source.remote


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.beln.data.MyResult
import com.test.beln.data.News
import com.test.beln.data.source.NewsDataSource
import kotlinx.coroutines.delay

object NewsRemoteDataSource : NewsDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var NEWS_SERVICE_DATA = LinkedHashMap<String, News>(2)


    init {
        addNews("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addNews("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    private val observableNews = MutableLiveData<MyResult<List<News>>>()


    override fun observeNews(): LiveData<MyResult<List<News>>> {
        return observableNews

    }

    override suspend fun getNews(): MyResult<List<News>> {
        // Simulate network by delaying the execution.
        val tasks = NEWS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return MyResult.Success(tasks)
    }

    override suspend fun refreshTasks() {
        observableNews.value = getNews()
    }

    private fun addNews(title: String, description: String) {
        val newNews = News(title, description)
        NEWS_SERVICE_DATA[newNews.id] = newNews
    }
}