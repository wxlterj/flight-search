package com.example.flightsearch.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearch.data.local.entity.AirportEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FlightSearchDao {
    @Query("SELECT * FROM airport WHERE name LIKE '%' ||:airportName || '%' OR iata_code LIKE '%' ||:airportName || '%'")
    fun searchAirport(airportName: String): Flow<List<AirportEntity>>
}