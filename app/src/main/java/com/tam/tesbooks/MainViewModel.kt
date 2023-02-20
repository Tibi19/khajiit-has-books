package com.tam.tesbooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.util.FALLBACK_ERROR_DATABASE
import com.tam.tesbooks.util.TIME_WAIT_FOR_UI_ON_FIRST_LOAD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _isLoadingData = MutableStateFlow(true)
    val isLoadingData = _isLoadingData.asStateFlow()

    private val _isLoadingUi = MutableStateFlow(true)
    val isLoadingUi = _isLoadingUi.asStateFlow()

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.initializeDatabase()
                .onResource(
                    onSuccess = {},
                    onError = {error -> errorChannel.send(error ?: FALLBACK_ERROR_DATABASE)}
                )
            _isLoadingData.value = false
            delay(TIME_WAIT_FOR_UI_ON_FIRST_LOAD)
            _isLoadingUi.value = false
        }
    }

}

