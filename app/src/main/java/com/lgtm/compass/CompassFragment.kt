package com.lgtm.compass

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lgtm.compass.data.CompassOrientationDataSource
import com.lgtm.compass.data.CompassRepository
import com.lgtm.compass.databinding.FragmentCompassBinding
import com.lgtm.compass.sensor.RotationVectorSensor
import com.lgtm.compass.viewmodels.CompassViewModel
import com.lgtm.compass.viewmodels.ViewModelFactory

class CompassFragment: Fragment() {

    private lateinit var binding: FragmentCompassBinding

    private lateinit var viewModel: CompassViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompassBinding.inflate(inflater, container, false)

        initViewModel()
        observeViewModel()

        return binding.root
    }

    private fun initViewModel() {
        val rotationVectorSensor = RotationVectorSensor(
            requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        ).also {
            lifecycle.addObserver(it)
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(CompassRepository(CompassOrientationDataSource(rotationVectorSensor)))
        ).get(CompassViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.getOrientation().observe(this) { compassOrientation ->
            Log.d("Doran", "compass orientation : ${compassOrientation}")
            updateCompassDirection(
                binding.textView,
                compassOrientation.lastOrientation,
                compassOrientation.newOrientation,
            )
        }
    }


    private fun updateCompassDirection(view: View, nextAzimuth: Float, currAzimuth: Float) {
        val anim = RotateAnimation(-currAzimuth, -nextAzimuth,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f).apply {
            duration = 300
            repeatCount = 0
            fillAfter = true
        }

        view.startAnimation(anim)
    }

}
