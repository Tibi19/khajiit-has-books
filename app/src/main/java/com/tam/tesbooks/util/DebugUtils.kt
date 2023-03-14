package com.tam.tesbooks.util

import android.content.Context
import android.util.Log.d
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.tam.tesbooks.data.json.dto.BookMetadataDto
import com.tam.tesbooks.data.json.dto.BookTextDto
import com.tam.tesbooks.data.room.entity.BookMetadataEntity
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book.BookParagraph
import com.tam.tesbooks.domain.model.book.ImageParagraph
import com.tam.tesbooks.domain.model.book.TextParagraph
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.metadata.BookMetadata
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.system.measureTimeMillis

const val TEST_TAG = "tmtest"

fun printTest(message: String) = d(TEST_TAG, message)

fun printMetadataTest(book: BookMetadataDto) = printTest("Book id ${book.id}: ${book.title} by ${book.author}")
fun printMetadataTest(book: BookMetadataEntity) = printTest("Book id ${book.id}: ${book.title} by ${book.author}")
fun printMetadataTest(book: BookMetadata) = printTest("Book id ${book.id}: ${book.title} by ${book.author}")

fun printBookInfos(bookInfos: List<BookInfo>) =
    bookInfos.forEach { printMetadataTest(it.metadata) }

fun infiniteFlow(timeout: Long = 1000) =
    flow {
        var i = 0
        while (true) {
            emit(i++)
            delay(timeout)
        }
    }

@Composable
fun InfiniteEffect(key: Any = true, timeout: Long = 1000, onEach: () -> Unit) {
    LaunchedEffect(key1 = key) {
        infiniteFlow(timeout)
            .collect {
                onEach()
            }
    }
}

fun printBookInfosWithLists(bookInfos: List<BookInfo>) =
    bookInfos.forEach { bookInfo ->
        printMetadataTest(bookInfo.metadata)
        if(bookInfo.savedInBookLists.isEmpty()) return@forEach
        val lists = bookInfo.savedInBookLists.joinToString(", ") { it.name }
        printTest("\tSaved in lists: $lists")
    }

fun getTestBookInfo() =
    BookInfo(
        bookId = 9999,
        metadata = BookMetadata(
            id = 9999,
            title = "Titleing Through Oblivion",
            author = "Long Tail",
            description = "There once was but not really, just for the sake of this testing and preview - a non-existant Elder Scroll",
            tags = listOf("Alchemy", "Journals, Notes & Correspondence", "TES2:Daggerfall"),
            category = "Journals, Notes & Correspondence",
            fileName = "nofile.json",
            textSize = 99,
            banner = "https://i.pinimg.com/236x/3a/78/06/3a7806e7d7a98e3cfdf9623331e150a1.jpg"
        ),
        savedInBookLists = listOf(
            BookList("Read", true, 1),
            BookList("Favorite", true, 3),
            BookList("Alchemy Stuff", false, 6)
        )
    )

fun getTestBookLists(): List<BookList> =
    listOf(
        BookList("Read", true, 1),
        BookList("Read Later", true, 2),
        BookList("Favorite", true, 3),
        BookList("Imperial wars", false, 4),
        BookList("Cool cats", false, 5),
        BookList("Alchemy Stuff", false, 6)
    )

fun getTestTextParagraph(): BookParagraph =
    TextParagraph(
        89,
        9,
        "Just a normal paragraph, not much going on with it or out of the ordinary. It's a pretty boring paragraph actually" +
                ", brought into existence for a measly preview. Truly, it would be lucky to reach runtime by pure chance alone..."
    )

fun getTestImageParagraph(): ImageParagraph =
    ImageParagraph(
        8,
        5,
        "<image=https://www.imperial-library.info/sites/default/files/mwbks_tslov_vivec_face.jpg>"
    )

fun getEmptyBookTextDto() =
    BookTextDto(
        id = -1,
        paragraphs = emptyList()
    )

inline fun <T> getMeasured(
    messageBeforeTime: String,
    get: () -> T
): T {
    var value: T
    val time = measureTimeMillis {
        value = get()
    }
    printTest("$messageBeforeTime $time")
    return value
}

inline fun doMeasured(
    messageBeforeTime: String,
    runnable: () -> Unit
) {
    val time = measureTimeMillis {
        runnable()
    }
    printTest("$messageBeforeTime $time")
}

fun showToast(context: Context, message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
