package com.lgtm.compass.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RotationVectorSensor(
    private val sensorManager: SensorManager,
    private val samplingPeriodUs: Int = SensorManager.SENSOR_DELAY_FASTEST
) : LifecycleEventObserver, SensorEventListener {

    private val sensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    // TODO : LiveData -> Flow
    private val _events = MutableLiveData<SensorEvent>()
    val events: LiveData<SensorEvent>
        get() = _events

    override fun onSensorChanged(event: SensorEvent) {
        _events.postValue(event)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_RESUME -> {
                sensorManager.registerListener(this, sensor, samplingPeriodUs)
            }
            Lifecycle.Event.ON_PAUSE -> {
                sensorManager.unregisterListener(this)
            }
            else -> {
                // do nothing
            }
        }
    }

}
