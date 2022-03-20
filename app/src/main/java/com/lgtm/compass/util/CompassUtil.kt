package com.lgtm.compass.util

import com.lgtm.compass.model.CompassOrientationVO

internal val POINTS = arrayListOf("북", "북동", "동", "남동", "남", "남서", "서", "북서")

internal fun CompassOrientationVO.getClosestPoint(): String {
    val index = (((newOrientation + 22.5f) % 360) / 45.0f).toInt()
    return POINTS[index]
}
