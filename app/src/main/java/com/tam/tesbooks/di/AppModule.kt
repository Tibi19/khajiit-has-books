package com.tam.tesbooks.di

import android.app.Application
import androidx.room.Room
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun provideBookInfoDatabase(app: Application): BookInfoDatabase =
        Room.databaseBuilder(
            app,
            BookInfoDatabase::class.java,
            "database_book_info"
        )
            .fallbackToDestructiveMigration()
            .build()

}