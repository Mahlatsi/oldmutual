package com.example.myapplication.ui.countries

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
class CountriesViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Loading)
    val uiState: StateFlow<CountriesUiState> = _uiState.asStateFlow()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _uiState.value = CountriesUiState.Loading
            repository.getCountries()
                .onSuccess { countries ->
                    _uiState.value = CountriesUiState.Success(countries)
                }
                .onFailure { error ->
                    _uiState.value = CountriesUiState.Error(error.message ?: "Unknown error occurred")
                }
        }
    }
}

sealed class CountriesUiState {
    data object Loading : CountriesUiState()
    data class Success(val countries: List<Country>) : CountriesUiState()
    data class Error(val message: String) : CountriesUiState()
}