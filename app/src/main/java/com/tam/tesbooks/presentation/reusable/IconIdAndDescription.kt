package com.tam.tesbooks.presentation.reusable

import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.util.CONTENT_MARKED_AS_FAVORITE
import com.tam.tesbooks.util.CONTENT_MARKED_AS_VIEWED
import com.tam.tesbooks.util.CONTENT_UNMARKED_AS_FAVORITE
import com.tam.tesbooks.util.CONTENT_UNMARKED_AS_VIEWED

fun getMarkViewedIconIdAndDescription(
    bookInfo: BookInfo,
    viewedBooksList: BookList
): Pair<Int, String> =
    getIconIdAndDescription(
        isBookMarked = bookInfo.savedInBookLists.contains(viewedBooksList),
        markedIconId = R.drawable.ic_read_book_checked_actionable,
        markedIconDescription = CONTENT_MARKED_AS_VIEWED,
        unmarkedIconId = R.drawable.ic_read_book,
        unmarkedIconDescription = CONTENT_UNMARKED_AS_VIEWED
    )

fun getFavoriteIconIdAndDescription(
    bookInfo: BookInfo,
    favoriteBooksList: BookList
): Pair<Int, String> =
    getIconIdAndDescription(
        isBookMarked = bookInfo.savedInBookLists.contains(favoriteBooksList),
        markedIconId = R.drawable.ic_favorite_filled_actionable,
        markedIconDescription = CONTENT_MARKED_AS_FAVORITE,
        unmarkedIconId = R.drawable.ic_favorite,
        unmarkedIconDescription = CONTENT_UNMARKED_AS_FAVORITE
    )

private fun getIconIdAndDescription(
    isBookMarked: Boolean,
    markedIconId: Int,
    markedIconDescription: String,
    unmarkedIconId: Int,
    unmarkedIconDescription: String
): Pair<Int, String> {
    val id = if (isBookMarked) markedIconId else unmarkedIconId
    val description = if (isBookMarked) markedIconDescription else unmarkedIconDescription
    return Pair(id, description)
}