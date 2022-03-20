package com.lgtm.compass.data

import androidx.lifecycle.LiveData
import com.lgtm.compass.model.CompassOrientationVO

interface OrientationDataSource {
    // TODO : LiveData -> Flow
    fun getOrientation(): LiveData<CompassOrientationVO>
}
