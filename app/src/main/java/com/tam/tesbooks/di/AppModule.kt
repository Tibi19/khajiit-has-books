package com.tam.tesbooks.di

import android.app.Application
import androidx.navigation.Navigator
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.util.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookInfoDatabase(app: Application): BookInfoDatabase =
        Room.databaseBuilder(
            app,
            BookInfoDatabase::class.java,
            DATABASE_BOOK_INFO
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    @Named(INSTANCE_IO_DISPATCHER)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Named(INSTANCE_DEFAULT_DISPATCHER)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    @Named(INSTANCE_APPLICATION_SCOPE)
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob())

}