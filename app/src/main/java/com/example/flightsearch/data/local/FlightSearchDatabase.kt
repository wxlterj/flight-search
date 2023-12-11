package com.example.flightsearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flightsearch.data.local.dao.FlightSearchDao
import com.example.flightsearch.data.local.entity.AirportEntity
import com.example.flightsearch.data.local.entity.FavoriteEntity

@Database(entities = [AirportEntity::class, FavoriteEntity::class], version = 1)
abstract class FlightSearchDatabase : RoomDatabase() {
    abstract fun getFlightSearchDao(): FlightSearchDao
}