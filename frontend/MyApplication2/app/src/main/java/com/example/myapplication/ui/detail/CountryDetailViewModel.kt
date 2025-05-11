package com.example.myapplication.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Country
import com.example.myapplication.data.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val repository: CountryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountryDetailUiState>(CountryDetailUiState.Loading)
    val uiState: StateFlow<CountryDetailUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("extra_country_name")?.let { name ->
            loadCountry(name)
        }
    }

    private fun loadCountry(name: String) {
        viewModelScope.launch {
            _uiState.value = CountryDetailUiState.Loading
            repository.getCountryByName(name)
                .onSuccess { country ->
                    _uiState.value = CountryDetailUiState.Success(country)
                }
                .onFailure { error ->
                    _uiState.value = CountryDetailUiState.Error(error.message ?: "Unknown error occurred")
                }
        }
    }
}

sealed class CountryDetailUiState {
    data object Loading : CountryDetailUiState()
    data class Success(val country: Country) : CountryDetailUiState()
    data class Error(val message: String) : CountryDetailUiState()
} 