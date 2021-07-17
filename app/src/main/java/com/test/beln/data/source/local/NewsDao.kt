package com.test.beln.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.beln.data.News

@Dao
interface NewsDao {

    /**
     * Observes list of tasks.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM news")
    fun observeTasks(): LiveData<List<News>>

    /**
     * Observes a single task.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM news WHERE id = :newsId")
    fun observeNewsById(newsId: String): LiveData<News>

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM news")
    suspend fun getNews(): List<News>

    /**
     * Select a task by id.
     *
     * @param taskId the task id.
     * @return the task with taskId.
     */
    @Query("SELECT * FROM News WHERE id = :taskId")
    suspend fun getNewsById(taskId: String): News?

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(news: News)

    /**
     * Update a task.
     *
     * @param task task to be updated
     * @return the number of tasks updated. This should always be 1.
     */
    @Update
    suspend fun updateTask(news: News): Int


    /**
     * Delete a task by id.
     *
     * @return the number of tasks deleted. This should always be 1.
     */
    @Query("DELETE FROM news WHERE id = :newsId")
    suspend fun deleteNewsById(newsId: String): Int

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM news")
    suspend fun deleteNews()


}