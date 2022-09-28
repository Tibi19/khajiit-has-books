package com.tam.tesbooks.presentation.dialog.filter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.util.FALLBACK_ERROR_LOAD_CATEGORIES
import com.tam.tesbooks.util.FALLBACK_ERROR_LOAD_TAGS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var state by mutableStateOf(FilterState())
        private set

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    init {
        loadTags()
        loadCategories()
    }

    private fun loadTags() =
        viewModelScope.launch {
            repository.getTags()
                .onResource(
                    { data ->
                        data ?: return@onResource
                        state = state.copy(tags = data)
                    },
                    { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_TAGS) }
                )
        }


    private fun loadCategories() =
        viewModelScope.launch {
            repository.getCategories()
                .onResource(
                    { data ->
                        data ?: return@onResource
                        state = state.copy(categories = data)
                    },
                    { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_CATEGORIES) }
                )
        }
}