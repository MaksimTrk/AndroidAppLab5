package com.steptracker.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.steptracker.app.data.AppDatabase
import com.steptracker.app.data.DayRecord
import com.steptracker.app.data.ProfileRepository
import com.steptracker.app.data.UserProfile
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.getInstance(app)
    private val dao = db.dayRecordDao()
    private val profileRepo = ProfileRepository(app)

    private val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today: String get() = LocalDate.now().format(fmt)

    // ---- Profile ----
    private val _profile = MutableLiveData(profileRepo.loadProfile())
    val profile: LiveData<UserProfile> = _profile

    fun saveProfile(p: UserProfile) {
        profileRepo.saveProfile(p)
        _profile.value = p
    }

    // ---- Today session ----
    private val _steps = MutableLiveData(0)
    val steps: LiveData<Int> = _steps

    private val _activeSeconds = MutableLiveData(0)
    val activeSeconds: LiveData<Int> = _activeSeconds

    private val _accelText = MutableLiveData("— — —")
    val accelText: LiveData<String> = _accelText

    private val _isTracking = MutableLiveData(false)
    val isTracking: LiveData<Boolean> = _isTracking

    // ---- History ----
    val last30Days: LiveData<List<DayRecord>> = dao.getLast30Days()

    private val _weekData = MutableLiveData<List<DayRecord>>(emptyList())
    val weekData: LiveData<List<DayRecord>> = _weekData

    fun loadWeekData() {
        viewModelScope.launch {
            _weekData.postValue(dao.getLast7DaysList())
        }
    }

    private val _avgSteps = MutableLiveData(0f)
    val avgSteps: LiveData<Float> = _avgSteps

    private val _avgCalories = MutableLiveData(0f)
    val avgCalories: LiveData<Float> = _avgCalories

    private val _totalKm = MutableLiveData(0f)
    val totalKm: LiveData<Float> = _totalKm

    fun loadStats() {
        viewModelScope.launch {
            _avgSteps.postValue(dao.avgSteps() ?: 0f)
            _avgCalories.postValue(dao.avgCalories() ?: 0f)
            _totalKm.postValue((dao.totalDistanceMeters() ?: 0f) / 1000f)
            _weekData.postValue(dao.getLast7DaysList())
        }
    }

    // ---- Sensor callbacks ----
    fun onStep() {
        val s = (_steps.value ?: 0) + 1
        _steps.postValue(s)
        persistToday(s)
    }

    fun onAccel(x: Float, y: Float, z: Float) {
        _accelText.postValue("%.2f  %.2f  %.2f".format(x, y, z))
    }

    fun setTracking(running: Boolean) {
        _isTracking.value = running
    }

    fun tickActiveSecond() {
        val s = (_activeSeconds.value ?: 0) + 1
        _activeSeconds.postValue(s)
    }

    fun resetToday() {
        _steps.value = 0
        _activeSeconds.value = 0
        _isTracking.value = false
        viewModelScope.launch {
            val p = profile.value ?: UserProfile()
            dao.insert(DayRecord(today, 0, 0, 0f, 0f))
        }
    }

    private fun persistToday(steps: Int) {
        viewModelScope.launch {
            val p = profile.value ?: UserProfile()
            val rec = DayRecord(
                date = today,
                steps = steps,
                activeSeconds = _activeSeconds.value ?: 0,
                distanceMeters = p.calcDistanceKm(steps) * 1000f,
                caloriesBurned = p.calcCalories(steps)
            )
            dao.insert(rec)
        }
    }
}
