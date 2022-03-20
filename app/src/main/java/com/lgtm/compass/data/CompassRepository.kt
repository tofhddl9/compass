package com.lgtm.compass.data

import androidx.lifecycle.LiveData

class CompassRepository(
    private val compassOrientationDataSource: OrientationDataSource
) : OrientationDataSource by compassOrientationDataSource {

}