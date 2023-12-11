package com.example.flightsearch.model.mappers

import com.example.flightsearch.data.local.entity.AirportEntity
import com.example.flightsearch.model.Airport

object AirportMapper {
    fun AirportEntity.toAirport(): Airport {
        return Airport(
            id = id,
            iataCode = iataCode,
            name = name,
            passenger = passengers
        )
    }
}