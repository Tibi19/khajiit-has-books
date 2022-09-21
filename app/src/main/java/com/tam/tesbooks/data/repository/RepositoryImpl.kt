package com.tam.tesbooks.data.repository

import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val jsonRepository: JsonRepository,
    private val bookListRepository: BookListRepository,
    private val bookInfoRepository: BookInfoRepository,
    private val bookmarkRepository: BookmarkRepository
): Repository {

    override suspend fun getBookInfo(id: Int): Resource<BookInfo> =
        bookInfoRepository.getBookInfo(id)

    override suspend fun initializeDatabase(): Resource<Unit> {
        jsonRepository.saveMetadatasFromJson()
            .onError { message ->
                return Resource.Error(message!!)
            }
        bookListRepository.saveDefaultBookLists()
        return Resource.Success()
    }

    override suspend fun getBookInfos(
        libraryOrder: LibraryOrder,
        libraryFilter: LibraryFilter,
        searchQuery: String,
        alreadyLoadedInfos: List<BookInfo>
    ): Flow<Resource<List<BookInfo>>> =
        bookInfoRepository.getBookInfos(libraryOrder, libraryFilter, alreadyLoadedInfos, searchQuery)

    override suspend fun getBookInfosFromList(
        bookList: BookList,
        bookListSort: BookListSort,
        alreadyLoadedInfos: List<BookInfo>
    ): Flow<Resource<List<BookInfo>>> =
        bookInfoRepository.getBookInfosFromList(bookList, bookListSort, alreadyLoadedInfos)

    override suspend fun getBookLists(): Resource<List<BookList>> =
        bookListRepository.getBookLists()

    override suspend fun addBookList(bookList: BookList) =
        bookListRepository.addBookList(bookList)

    override suspend fun editBookList(bookList: BookList) =
        bookListRepository.editBookList(bookList)

    override suspend fun removeBookList(bookList: BookList) =
        bookListRepository.removeBookList(bookList)

    override suspend fun addBookToList(bookInfo: BookInfo, bookList: BookList) =
        bookListRepository.addBookToList(bookInfo, bookList)

    override suspend fun removeBookFromList(bookInfo: BookInfo, bookList: BookList) =
        bookListRepository.removeBookFromList(bookInfo, bookList)

    override suspend fun getBookmarks(lastLoadedBookmark: Bookmark?): Flow<Resource<List<Bookmark>>> =
        bookmarkRepository.getBookmarks(lastLoadedBookmark)

    override suspend fun getBookmarksOfBookId(bookId: Int): Flow<Resource<List<Bookmark>>> =
        bookmarkRepository.getBookmarksOfBookId(bookId)

    override suspend fun addBookmark(bookmark: Bookmark) =
        bookmarkRepository.addBookmark(bookmark)

    override suspend fun removeBookmark(bookmark: Bookmark) =
        bookmarkRepository.removeBookmark(bookmark)

    override suspend fun getBook(bookId: Int): Flow<Resource<Book>> =
        jsonRepository.getBook(bookId)

    override suspend fun getCategories(): Resource<List<String>> =
        jsonRepository.getCategories()

    override suspend fun getTags(): Resource<List<String>> =
        jsonRepository.getTags()
}
