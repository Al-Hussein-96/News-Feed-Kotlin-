package com.test.beln.data.source.remote


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.beln.data.MyResult
import com.test.beln.data.News
import com.test.beln.data.source.NewsDataSource
import kotlinx.coroutines.delay
import timber.log.Timber

object NewsRemoteDataSource : NewsDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var NEWS_SERVICE_DATA = LinkedHashMap<String, News>(2)


    init {
        addNews("Build tower in Pisa", "Ground looks good, no foundation work required.","https://assets.nst.com.my/images/articles/03xxworld_1596384698.jpg")
        addNews("Finish bridge in Tacoma", "Found awesome girders at half the cost!","https://www.joelapompe.net/wp-content/uploads/2019/01/bottle-natural-mont-dore-2017.jpg")
        addNews("Finish bridge in Tacoma", "Found awesome girders at half the cost!","http://www.jkuat.ac.ke/departments/transport/wp-content/uploads/2018/11/natural-butterfly-image-for-mobile-1.jpg")
        addNews("Finish bridge in Tacoma", "Found awesome girders at half the cost!","https://assets.nst.com.my/images/articles/03xxworld_1596384698.jpg")
    }

    private val observableNews = MutableLiveData<MyResult<List<News>>>()


    override fun observeNews(): LiveData<MyResult<List<News>>> {
        return observableNews

    }

    override suspend fun getNews(newsId: String): MyResult<News> {
        // Simulate network by delaying the execution.
        delay(SERVICE_LATENCY_IN_MILLIS)
        NEWS_SERVICE_DATA[newsId]?.let {
            return MyResult.Success(it)
        }
        return MyResult.Error(Exception("News not found"))
    }

    override suspend fun saveNews(news: News) {
        NEWS_SERVICE_DATA[news.id] = news

    }

    override suspend fun deleteAllNews() {
        NEWS_SERVICE_DATA.clear()
    }

    override suspend fun deleteNewsLazy(newsId: String) {
        NEWS_SERVICE_DATA.remove(newsId)
    }

    override suspend fun getAllNewsFeed(): MyResult<List<News>> {
        // Simulate network by delaying the execution.
        Timber.tag("Mohammad").i("get All News Feed (Remote Repository)")
        val tasks = NEWS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return MyResult.Success(tasks)
    }

    override suspend fun refreshTasks() {
        observableNews.value = getAllNewsFeed()
    }

    private fun addNews(title: String, description: String,imageUrl: String) {
        val newNews = News(title = title,description = description,imageUrl = imageUrl)
        NEWS_SERVICE_DATA[newNews.id] = newNews
    }
}