package com.example.flightsearch.ui.favorites

import com.example.flightsearch.model.Airport

data class FavoriteScreenUiState(
    val searchText: String = "",
    val airports: List<Airport> = emptyList(),
    val isSearch: Boolean = false,
    val airportSelectedName: String = "",
    val airportSelectedIataCode: String = "",
)