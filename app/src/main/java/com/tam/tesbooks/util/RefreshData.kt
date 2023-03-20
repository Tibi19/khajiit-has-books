package com.tam.tesbooks.util

import com.tam.tesbooks.domain.repository.ChangingData
import com.tam.tesbooks.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun refreshDataObserver(
    viewModelScope: CoroutineScope,
    repository: Repository,
    crossinline refreshBookmarks : () -> Unit = {},
    crossinline refreshBookLists : () -> Unit = {},
    crossinline refreshBookSavedInLists : (bookInfoId: Int) -> Unit = {}
) = viewModelScope.launch {
    repository.getDataChangeSharedFlow()
        .collect { changingData ->
            when(changingData) {
                is ChangingData.Bookmarks -> refreshBookmarks()
                is ChangingData.BookLists -> refreshBookLists()
                is ChangingData.BookSavedInLists -> refreshBookSavedInLists(changingData.bookInfoId)
            }
        }
    }