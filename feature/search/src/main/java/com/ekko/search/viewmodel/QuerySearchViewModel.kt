package com.ekko.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuerySearchViewModel @Inject constructor() : ViewModel() {

    private val _searchQueryFlow = MutableSharedFlow<String>()
    private val _preSearchQueryFlow = MutableStateFlow<String?>(null)

    fun submitPreSearchQuery(search: String) {
        _preSearchQueryFlow.value = search
    }

    @OptIn(FlowPreview::class)
    fun preSearchQuery(debounce: Long = 500): Flow<String> {
        return _preSearchQueryFlow.filterNotNull().distinctUntilChanged().debounce(debounce)
    }

    fun submitSearchQuery(search: String) {
        viewModelScope.launch {
            _searchQueryFlow.emit(search)
        }
    }

    fun searchQuery(): Flow<String> {
        return _searchQueryFlow.filter {
            it.isNotEmpty()
        }
    }
}