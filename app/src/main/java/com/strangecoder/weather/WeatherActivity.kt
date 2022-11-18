package com.strangecoder.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.strangecoder.weather.databinding.ActivityWeatherBinding
import com.strangecoder.weather.models.WeatherResponse
import com.strangecoder.weather.network.RetrofitService
import kotlinx.coroutines.launch

const val LOCATION_PERMISSION_REQ_CODE = 101

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val _weatherLiveData = MutableLiveData<WeatherResponse>()
    private val weatherResponse: LiveData<WeatherResponse> get() = _weatherLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()
        subscribeToObservers()
    }

    private fun getWeatherInfo(lat: Double, lon: Double) {
        lifecycleScope.launch {
            try {
                val result = RetrofitService.service.getWeatherInfo(latitude = lat, longitude = lon)
                _weatherLiveData.postValue(result)
            } catch (throwable: Throwable) {
                Toast.makeText(this@WeatherActivity, "Something went wrong:(", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQ_CODE)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                getWeatherInfo(location.latitude, location.longitude)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to get location!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun subscribeToObservers() {
        weatherResponse.observe(this, { it ->
            it?.let { response ->
                response.main?.let {
                    val temp = (it.temp?.minus(273.15)).toString().split(".")[0]
                    binding.tempCurrent.text = "${temp}°C"
                    val tempHigh = (it.tempMax?.minus(273.15)).toString().split(".")[0]
                    binding.tempHigh.text = "Max: ${tempHigh}°C"
                    val tempLow = (it.tempMin?.minus(273.15)).toString().split(".")[0]
                    binding.tempLow.text = "Min: ${tempLow}°C"
                }
                binding.cityName.text = "${response.name}, ${response.sys?.country}"
                binding.weatherInfoText.text = response.weather?.get(0)?.main ?: ""
                Glide.with(binding.weatherInfoImage)
                    .load("https://openweathermap.org/img/wn/${response.weather?.get(0)?.icon}@4x.png")
                    .into(binding.weatherInfoImage)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    getCurrentLocation()
                } else {
                    Toast.makeText(
                        this, "Location permission required to get weather info.",
                        Toast.LENGTH_SHORT
                    ).show()
                    getCurrentLocation()
                }
            }
        }
    }
}