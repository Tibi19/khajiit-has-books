package com.tam.tesbooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.data.repository.RepositoryTest
import com.tam.tesbooks.util.printTest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: RepositoryTest
): ViewModel() {

    fun showTest() {
        printTest("")
        //testBookText()
//        viewModelScope.launch {
//            repository.testTagsAndCategories()
//        }
        testBooksMetadata()
    }

    private fun testBookText() =
        viewModelScope.launch {
            val id = 300
            val bookMetadata = repository.testBooksMetadata()?.booksMetadata?.get(id) ?: return@launch
            val time = measureTimeMillis {
                repository.testBookText(bookMetadata.fileName)?.let {
                    printTest("Text of book $id, ${bookMetadata.title} by ${bookMetadata.author}")
                    it.paragraphs.forEach { paragraph ->
                        printTest(paragraph)
                    }
                }
            }
            printTest("Tested book text in $time")
        }

    private fun testBooksMetadata() =
        viewModelScope.launch {
            val time = measureTimeMillis {
                repository.testBooksMetadata()?.booksMetadata?.let {
                    val id = 4000
                    val book = it[id]
                    val title = book.title
                    val author = book.author
                    printTest("Book $id is $title by $author")
                }
            }
            printTest("Tested books metadata in $time")
        }

    private fun testTags() =
        viewModelScope.launch {
            repository.testTags().forEach {
                printTest("tag: $it")
            }
        }

}