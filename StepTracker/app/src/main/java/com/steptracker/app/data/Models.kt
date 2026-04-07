package com.steptracker.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_records")
data class DayRecord(
    @PrimaryKey
    val date: String,
    val steps: Int = 0,
    val activeSeconds: Int = 0,
    val distanceMeters: Float = 0f,
    val caloriesBurned: Float = 0f
)

data class UserProfile(
    val weightKg: Float = 70f,
    val heightCm: Int = 170,
    val ageYears: Int = 25,
    val isMale: Boolean = true,
    val dailyGoalSteps: Int = 10000,
    val strideLengthCm: Int = 75
) {
    val bmr: Float get() = if (isMale)
        10f * weightKg + 6.25f * heightCm - 5f * ageYears + 5f
    else
        10f * weightKg + 6.25f * heightCm - 5f * ageYears - 161f

    val bmi: Float get() = weightKg / ((heightCm / 100f) * (heightCm / 100f))

    fun calcCalories(steps: Int): Float {
        val distKm = steps * strideLengthCm / 100f / 1000f
        return weightKg * 3.5f * (distKm / 5f)
    }

    fun calcDistanceKm(steps: Int): Float =
        steps * strideLengthCm / 100f / 1000f
}