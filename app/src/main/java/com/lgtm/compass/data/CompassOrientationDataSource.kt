package com.lgtm.compass.data

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.lgtm.compass.model.CompassOrientationVO
import com.lgtm.compass.sensor.RotationVectorSensor

class CompassOrientationDataSource(
    private val sensor: RotationVectorSensor
) : OrientationDataSource {

    private val rotationMatrix = FloatArray(16)
    private val orientation = FloatArray(3)
    private val orientationDegree = FloatArray(3)

    private var lastOrientation = 0.0f

    //TODO : LiveData -> Flow
    override fun getOrientation(): LiveData<CompassOrientationVO> {
        return sensor.events.map { event ->
            var newOrientation = (orientationDegree[0] + 360f) % 360

            event.sensor?.let { sensor ->
                if (sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    SensorManager.getOrientation(rotationMatrix,
                        this.orientation
                    )

                    orientationDegree[0] = Math.toDegrees(orientation[0].toDouble()).toFloat()
                    newOrientation = (orientationDegree[0] + 360f) % 360
                }
            }

            val orientation = CompassOrientationVO(lastOrientation = lastOrientation, newOrientation = newOrientation)
            lastOrientation = newOrientation

            orientation
        }
    }
}
