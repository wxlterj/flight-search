package com.example.flightsearch.data

import com.example.flightsearch.data.local.dao.FlightSearchDao
import com.example.flightsearch.model.Airport
import com.example.flightsearch.model.mappers.AirportMapper.toAirport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


class AirportRepository @Inject constructor(
    private val flightSearchDao: FlightSearchDao
) {
    fun searchAirport(airportName: String): Flow<List<Airport>> {
        return flightSearchDao.searchAirport(airportName).mapLatest {
            it.map { airportEntity -> airportEntity.toAirport() }
        }
    }
}