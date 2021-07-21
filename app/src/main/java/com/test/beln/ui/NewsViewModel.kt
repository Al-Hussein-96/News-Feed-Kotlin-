package com.test.beln.ui

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.test.beln.data.MyResult

import com.test.beln.data.News
import com.test.beln.data.source.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for the task list screen.
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _items: LiveData<List<News>> = _forceUpdate.switchMap { forceUpdate ->
        Timber.tag("Mohammad").i("_items: forceUpdate")
        if (forceUpdate) {
//            _dataLoading.value = true
            viewModelScope.launch {
                newsRepository.refreshNews()
//                _dataLoading.value = false
            }
        }
        newsRepository.observeNews().distinctUntilChanged().switchMap { filterNews(it) }
    }

    val items: LiveData<List<News>> = _items


    private fun filterNews(newsResult: MyResult<List<News>>): LiveData<List<News>> {
        Timber.tag("Mohammad").i("newsResult %s", newsResult.toString())
        // TODO: This is a good case for liveData builder. Replace when stable.
        val result = MutableLiveData<List<News>>()

        if (newsResult is MyResult.Success) {
//            isDataLoadingError.value = false
            viewModelScope.launch {
                result.value = filterItems(newsResult.data, getSavedFilterType())
            }
        } else {
            result.value = emptyList()
//            showSnackbarMessage(R.string.loading_tasks_error)
//            isDataLoadingError.value = true
        }

        return result
    }


    private fun filterItems(news: List<News>, filteringType: NewsFilterType): List<News> {
        val newsToShow = ArrayList<News>()
        // We filter the tasks based on the requestType
        for (item in news) {
            when (filteringType) {
                NewsFilterType.FEED_NEWS -> newsToShow.add(item)
                NewsFilterType.TRASH_NEWS -> if (item.isDeleted) {
                    newsToShow.add(item)
                }
            }
        }
        return newsToShow
    }

    private fun getSavedFilterType(): NewsFilterType {
        return savedStateHandle.get(TASKS_FILTER_SAVED_STATE_KEY) ?: NewsFilterType.FEED_NEWS
    }

    private fun setFiltering(requestType: NewsFilterType) {
        savedStateHandle.set(TASKS_FILTER_SAVED_STATE_KEY, requestType)

        // Depending on the filter type, set the filtering label, icon drawables, etc.
        when (requestType) {
            NewsFilterType.FEED_NEWS -> {
//                setFilter(
//                    R.string.label_all, R.string.no_tasks_all,
//                    R.drawable.logo_no_fill, true
//                )
            }
            NewsFilterType.TRASH_NEWS -> {
//                setFilter(
//                    R.string.label_active, R.string.no_tasks_active,
//                    R.drawable.ic_check_circle_96dp, false
//                )
            }

        }
        // Refresh list
        loadNews(false)
    }

    /**
     * @param forceUpdate Pass in true to refresh the data in the [TasksDataSource]
     */
    private fun loadNews(forceUpdate: Boolean) {
        Timber.tag("Mohammad").i("loadNews %s %s", forceUpdate, _forceUpdate.value)
        _forceUpdate.value = forceUpdate
    }

    init {
        // Set initial state
//        setFiltering(getSavedFilterType())
        Timber.tag("Mohammad").i("Init")

        loadNews(true)
    }


////    val items: LiveData<List<News>> = _items
//
//    private val _dataLoading = MutableLiveData<Boolean>()
//    val dataLoading: LiveData<Boolean> = _dataLoading
//
//    private val _currentFilteringLabel = MutableLiveData<Int>()
//    val currentFilteringLabel: LiveData<Int> = _currentFilteringLabel
//
//    private val _noTasksLabel = MutableLiveData<Int>()
//    val noTasksLabel: LiveData<Int> = _noTasksLabel
//
//    private val _noTaskIconRes = MutableLiveData<Int>()
//    val noTaskIconRes: LiveData<Int> = _noTaskIconRes
//
//    private val _tasksAddViewVisible = MutableLiveData<Boolean>()
//    val tasksAddViewVisible: LiveData<Boolean> = _tasksAddViewVisible
//
////    private val _snackbarText = MutableLiveData<Event<Int>>()
////    val snackbarText: LiveData<Event<Int>> = _snackbarText
//
//    // Not used at the moment
//    private val isDataLoadingError = MutableLiveData<Boolean>()
//
//
//    private var resultMessageShown: Boolean = false
//
//    // This LiveData depends on another so we can use a transformation.
////    val empty: LiveData<Boolean> = Transformations.map(_items) {
////        it.isEmpty()
////    }
//
//

//
//    fun refresh() {
//        _forceUpdate.value = true
//    }
//
//    fun start() {
//        Timber.tag("Mohammad").i("items: %s",_items.value)
//        loadNews(!_forceUpdate.value!!)
//
//    }

}

// Used to save the current filtering in SavedStateHandle.
const val TASKS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"