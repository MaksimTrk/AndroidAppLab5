package com.steptracker.app.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DayRecordDao {

    @Query("SELECT * FROM day_records WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): DayRecord?

    @Query("SELECT * FROM day_records ORDER BY date DESC LIMIT 30")
    fun getLast30Days(): LiveData<List<DayRecord>>

    @Query("SELECT * FROM day_records ORDER BY date DESC LIMIT 7")
    suspend fun getLast7DaysList(): List<DayRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: DayRecord)

    @Update
    suspend fun update(record: DayRecord)

    @Query("SELECT AVG(steps) FROM day_records WHERE steps > 0")
    suspend fun avgSteps(): Float?

    @Query("SELECT AVG(caloriesBurned) FROM day_records WHERE steps > 0")
    suspend fun avgCalories(): Float?

    @Query("SELECT SUM(distanceMeters) FROM day_records")
    suspend fun totalDistanceMeters(): Float?
}
