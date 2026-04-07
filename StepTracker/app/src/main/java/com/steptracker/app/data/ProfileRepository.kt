package com.steptracker.app.data

import android.content.Context
import android.content.SharedPreferences

class ProfileRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)

    fun loadProfile(): UserProfile = UserProfile(
        weightKg = prefs.getFloat("weight", 70f),
        heightCm = prefs.getInt("height", 170),
        ageYears = prefs.getInt("age", 25),
        isMale = prefs.getBoolean("is_male", true),
        dailyGoalSteps = prefs.getInt("goal_steps", 10000),
        strideLengthCm = prefs.getInt("stride_cm", 75)
    )

    fun saveProfile(profile: UserProfile) {
        prefs.edit()
            .putFloat("weight", profile.weightKg)
            .putInt("height", profile.heightCm)
            .putInt("age", profile.ageYears)
            .putBoolean("is_male", profile.isMale)
            .putInt("goal_steps", profile.dailyGoalSteps)
            .putInt("stride_cm", profile.strideLengthCm)
            .apply()
    }
}
