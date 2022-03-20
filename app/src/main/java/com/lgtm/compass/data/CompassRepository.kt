package com.lgtm.compass.data

class CompassRepository(
    private val compassOrientationDataSource: OrientationDataSource
) : OrientationDataSource by compassOrientationDataSource {

}