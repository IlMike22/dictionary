package com.mind.market.dictionary.feature_dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mind.market.dictionary.core.util.Resource
import com.mind.market.dictionary.feature_dictionary.domain.use_case.GetWordInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetWordInfoViewModel @Inject constructor(
    val getGetWordInfoUseCase: GetWordInfoUseCase
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(500L) // todo create constant for that
            getGetWordInfoUseCase(query).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            wordInfoItems = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            wordInfoItems = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error" // todo extract string
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            wordInfoItems = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}