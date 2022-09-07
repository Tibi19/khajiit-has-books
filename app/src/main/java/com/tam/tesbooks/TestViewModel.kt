package com.tam.tesbooks

import android.util.Log.d
import androidx.lifecycle.ViewModel
import com.tam.tesbooks.data.repository.RepositoryTest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: RepositoryTest
): ViewModel() {

    init {
        d("TEST_REPO", "inited")
    }

    fun showTest() {
        val testList = repository.testList()
        d("TEST_REPO", "Test list with id ${testList.id} and name ${testList.name} which is default: ${testList.isDefault}")
    }

}