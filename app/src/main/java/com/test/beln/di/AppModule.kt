package com.test.beln.di

import android.content.Context
import androidx.room.Room
import com.test.beln.data.source.DefaultNewsRepository
import com.test.beln.data.source.NewsDataSource
import com.test.beln.data.source.NewsRepository
import com.test.beln.data.source.local.NewsDatabase
import com.test.beln.data.source.local.NewsLocalDataSource
import com.test.beln.data.source.remote.NewsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteNewsDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalNewsDataSource

    @Singleton
    @RemoteNewsDataSource
    @Provides
    fun provideNewsRemoteDataSource(): NewsDataSource {
        return NewsRemoteDataSource
    }

    @Singleton
    @LocalNewsDataSource
    @Provides
    fun provideNewsLocalDataSource(
        database: NewsDatabase,
        ioDispatcher: CoroutineDispatcher
    ): NewsDataSource {
        return NewsLocalDataSource(
            database.newsDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "News.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

/**
 * The binding for TasksRepository is on its own module so that we can replace it easily in tests.
 */
@Module
@InstallIn(SingletonComponent::class)
object NewsRepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        @AppModule.RemoteNewsDataSource remoteNewsDataSource: NewsDataSource,
        @AppModule.LocalNewsDataSource localNewsDataSource: NewsDataSource,
        ioDispatcher: CoroutineDispatcher
    ): NewsRepository {
        return DefaultNewsRepository(
            remoteNewsDataSource, localNewsDataSource, ioDispatcher
        )
    }
}