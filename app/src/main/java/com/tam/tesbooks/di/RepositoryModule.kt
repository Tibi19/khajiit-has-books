package com.tam.tesbooks.di

import com.tam.tesbooks.data.json.JsonLoader
import com.tam.tesbooks.data.json.JsonLoaderImpl
import com.tam.tesbooks.data.repository.*
import com.tam.tesbooks.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindJsonLoader(jsonLoaderImpl: JsonLoaderImpl): JsonLoader

    @Binds
    @Singleton
    abstract fun bindJsonRepository(jsonRepositoryImpl: JsonRepositoryImpl): JsonRepository

    @Binds
    @Singleton
    abstract fun bindBookInfoRepository(bookInfoRepositoryImpl: BookInfoRepositoryImpl): BookInfoRepository

    @Binds
    @Singleton
    abstract fun bindBookListRepository(bookListRepositoryImpl: BookListRepositoryImpl): BookListRepository

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(bookmarkRepositoryImpl: BookmarkRepositoryImpl): BookmarkRepository

    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

}