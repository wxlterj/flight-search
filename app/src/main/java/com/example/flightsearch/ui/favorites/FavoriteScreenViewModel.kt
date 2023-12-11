package com.example.flightsearch.ui.favorites


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.data.AirportRepository
import com.example.flightsearch.model.Airport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val airportRepository: AirportRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onSearchBarClicked() {
        _uiState.update {
            it.copy(
                isSearch = true
            )
        }
    }

    fun searchAirport(searchText: String) {
        _uiState.update {
            it.copy(
                searchText = searchText
            )
        }
        viewModelScope.launch {
            airportRepository.searchAirport(searchText).collectLatest { airports ->
                _uiState.update {
                    it.copy(
                        airports = if (searchText.isNotEmpty()) airports else emptyList()
                    )
                }
            }
        }
    }

    fun updateAirportDetails(airport: Airport) {
        _uiState.update {
            it.copy(
                airportSelectedName = airport.name,
                airportSelectedIataCode = airport.iataCode
            )
        }
    }

    fun clearSearch() {
        _uiState.update {
            it.copy(
                searchText = "",
                isSearch = false
            )
        }
    }
}