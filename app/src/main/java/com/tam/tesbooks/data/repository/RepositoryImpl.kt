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
        alreadyLoadedInfos: List<BookInfo>,
        searchQuery: String
    ): Flow<Resource<List<BookInfo>>> =
        bookInfoRepository.getBookInfos(libraryOrder, libraryFilter, alreadyLoadedInfos, searchQuery)

    // TODO in book info repo
    override suspend fun getBookInfosFromList(
        bookList: BookList,
        bookListSort: BookListSort,
        alreadyLoadedInfos: List<BookInfo>
    ): Flow<Resource<List<BookInfo>>> =
        bookInfoRepository.getBookInfosFromList(bookList, bookListSort, alreadyLoadedInfos)

    override suspend fun getBookLists(): Resource<List<BookList>> =
        bookListRepository.getBookLists()

    // TODO in book list repo
    override suspend fun addBookList(bookList: BookList) =
        bookListRepository.addBookList(bookList)

    // TODO in book list repo
    override suspend fun editBookList(bookList: BookList) =
        bookListRepository.editBookList(bookList)

    // TODO in book list repo
    override suspend fun removeBookList(bookList: BookList) =
        bookListRepository.removeBookList(bookList)

    override suspend fun addBookToList(bookInfo: BookInfo, bookList: BookList) =
        bookListRepository.removeBookFromList(bookInfo, bookList)

    // TODO in book list repo
    override suspend fun removeBookFromList(bookInfo: BookInfo, bookList: BookList) =
        bookListRepository.removeBookFromList(bookInfo, bookList)

    // TODO in bookmark repo
    override suspend fun getBookmarks(lastLoadedBookmark: Bookmark?): Flow<Resource<List<Bookmark>>> =
        bookmarkRepository.getBookmarks(lastLoadedBookmark)

    // TODO in bookmark repo
    override suspend fun addBookmark(bookmark: Bookmark) =
        bookmarkRepository.addBookmark(bookmark)

    // TODO in bookmark repo
    override suspend fun removeBookmark(bookmark: Bookmark) =
        bookmarkRepository.removeBookmark(bookmark)

    // TODO in json repo
    override suspend fun getBook(bookInfo: BookInfo): Resource<Book> =
        jsonRepository.getBook(bookInfo)

    override suspend fun getCategories(): Resource<List<String>> =
        jsonRepository.getCategories()

    override suspend fun getTags(): Resource<List<String>> =
        jsonRepository.getTags()
}
