package com.steptracker.app.ui.today

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.steptracker.app.MainViewModel
import com.steptracker.app.R
import com.steptracker.app.databinding.FragmentTodayBinding
import com.steptracker.app.sensor.StepSensor
import java.util.Locale

class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    private val vm: MainViewModel by activityViewModels()

    private lateinit var stepSensor: StepSensor
    private val handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stepSensor = StepSensor(
            requireContext(),
            onStep = { vm.onStep() },
            onAccel = { x, y, z -> vm.onAccel(x, y, z) }
        )

        vm.steps.observe(viewLifecycleOwner) { updateUI(it) }
        vm.activeSeconds.observe(viewLifecycleOwner) { updateTimer(it) }
        vm.accelText.observe(viewLifecycleOwner) { binding.tvAccel.text = it }
        vm.isTracking.observe(viewLifecycleOwner) { running ->
            binding.btnStart.text = if (running) getString(R.string.stop) else getString(R.string.start)
            binding.btnStart.isSelected = running
        }

        binding.btnStart.setOnClickListener { toggleTracking() }
        binding.btnReset.setOnClickListener {
            stopTracking()
            vm.resetToday()
        }

        val sensorType = if (stepSensor.hasSteDetector())
            "Step Detector" else if (stepSensor.hasAccelerometer())
            "Accelerometer" else "Немає датчика"
        binding.tvSensorType.text = sensorType
    }

    private fun toggleTracking() {
        if (vm.isTracking.value == true) stopTracking() else startTracking()
    }

    private fun startTracking() {
        vm.setTracking(true)
        stepSensor.register()
        startTimer()
    }

    private fun stopTracking() {
        vm.setTracking(false)
        stepSensor.unregister()
        stopTimer()
    }

    private fun startTimer() {
        val r = object : Runnable {
            override fun run() {
                vm.tickActiveSecond()
                handler.postDelayed(this, 1000)
            }
        }
        timerRunnable = r
        handler.postDelayed(r, 1000)
    }

    private fun stopTimer() {
        timerRunnable?.let { handler.removeCallbacks(it) }
    }

    private fun updateUI(steps: Int) {
        val profile = vm.profile.value ?: return
        val goal = profile.dailyGoalSteps
        val pct = (steps.toFloat() / goal * 100).toInt().coerceAtMost(100)

        binding.tvSteps.text = "%,d".format(steps).replace(',', ' ')
        binding.tvGoalPct.text = "$pct% мети"
        binding.progressRing.progress = pct

        val km = profile.calcDistanceKm(steps)
        val kcal = profile.calcCalories(steps)
        binding.tvKm.text = "%.2f".format(km)
        binding.tvKcal.text = kcal.toInt().toString()

        binding.tvTip.text = when {
            steps == 0 -> "Натисніть «Почати», щоб розпочати відлік"
            steps < 2000 -> "Гарний початок! Залишилось ${"%,d".format(goal - steps).replace(',', ' ')} кроків"
            steps < 5000 -> "Ви вже спалили ${kcal.toInt()} ккал. Так тримати!"
            steps < goal -> "Майже! Ще ${"%,d".format(goal - steps).replace(',', ' ')} до мети"
            else -> "Ціль досягнута! Пройдено ${"%.2f".format(km)} км та спалено ${kcal.toInt()} ккал"
        }
    }

    private fun updateTimer(seconds: Int) {
        val m = seconds / 60
        val s = seconds % 60
        binding.tvTime.text = String.format(Locale.getDefault(), "%d:%02d", m, s)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopTracking()
        _binding = null
    }
}
