package com.lgtm.compass.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lgtm.compass.data.CompassRepository
import com.lgtm.compass.model.CompassOrientationVO
import java.lang.IllegalArgumentException

class CompassViewModel(
    private val compassRepository: CompassRepository
) : ViewModel() {

    fun getOrientation(): LiveData<CompassOrientationVO> {
        return compassRepository.getOrientation()
    }

}

class ViewModelFactory(
    private val compassRepository: CompassRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompassViewModel::class.java)) {
            return CompassViewModel(compassRepository) as T
        }
        throw IllegalArgumentException("cannot create CompassViewModel")
    }

}
