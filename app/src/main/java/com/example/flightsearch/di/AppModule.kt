package com.example.flightsearch.di

import android.content.Context
import androidx.room.Room
import com.example.flightsearch.data.AirportRepository
import com.example.flightsearch.data.local.FlightSearchDatabase
import com.example.flightsearch.data.local.dao.FlightSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFlightSearchDatabase(@ApplicationContext context: Context): FlightSearchDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = FlightSearchDatabase::class.java,
            name = "flight_search"
        )
            .createFromAsset("database/flight_search.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideFlightSearchDao(database: FlightSearchDatabase): FlightSearchDao {
        return database.getFlightSearchDao()
    }

    @Provides
    @Singleton
    fun provideAirportRepository(flightSearchDao: FlightSearchDao): AirportRepository {
        return AirportRepository(flightSearchDao)
    }
}