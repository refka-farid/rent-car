package com.bravedroid.rentcar.shared.presentation.cars

import androidx.lifecycle.LiveData

interface CarScreenViewModel {
    val allCars: LiveData<List<CarUiModel>>
    fun loadContent()
}
