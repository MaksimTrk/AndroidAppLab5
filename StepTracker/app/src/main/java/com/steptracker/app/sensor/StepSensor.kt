package com.steptracker.app.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt


class StepSensor(
    context: Context,
    private val onStep: () -> Unit,
    private val onAccel: (x: Float, y: Float, z: Float) -> Unit
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val stepDetector: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    private val accelerometer: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val usingStepDetector get() = stepDetector != null

    // Accelerometer peak-detection state
    private var lastMag = 0.0
    private var lastStepTime = 0L
    private val magBuffer = ArrayDeque<Double>(8)

    fun register() {
        if (usingStepDetector) {
            sensorManager.registerListener(
                this,
                stepDetector,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else {
            accelerometer?.let {
                sensorManager.registerListener(
                    this,
                    it,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {

            Sensor.TYPE_STEP_DETECTOR -> {
                onStep()
            }

            Sensor.TYPE_ACCELEROMETER -> {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                onAccel(x, y, z)

                val mag = sqrt((x * x + y * y + z * z).toDouble())

                magBuffer.addLast(mag)
                if (magBuffer.size > 8) magBuffer.removeFirst()

                val avg = magBuffer.average()
                val now = System.currentTimeMillis()

                if (mag > avg + 1.2 && lastMag <= avg + 1.2 && (now - lastStepTime) > 350) {
                    lastStepTime = now
                    onStep()
                }
                lastMag = mag
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    fun hasSteDetector() = usingStepDetector
    fun hasAccelerometer() = accelerometer != null
}
