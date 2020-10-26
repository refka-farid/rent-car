package com.bravedroid.rentcar.flow.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedroid.rentcar.flow.domain.cars.GetAllCarsUseCase
import com.bravedroid.rentcar.shared.domain.Car
import com.bravedroid.rentcar.shared.presentation.cars.CarScreenViewModel
import com.bravedroid.rentcar.shared.presentation.cars.CarUiModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext

class CarsScreenViewModelImpl(
    private val parentCoroutineContext: CoroutineContext,
    private val getAllCarsUseCase: GetAllCarsUseCase,
) : ViewModel(), CoroutineScope, CarScreenViewModel {
    private val supervisorJob = SupervisorJob(parentCoroutineContext[Job])

    override val coroutineContext: CoroutineContext
        get() = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception occurred in viewModel ${throwable.message}  \n in $coroutineContext")
        } + parentCoroutineContext + supervisorJob

    private val _allCars = MutableLiveData<List<CarUiModel>>()
    override val allCars: LiveData<List<CarUiModel>> = _allCars

    override fun loadContent() {
        launch(coroutineContext) {
            getAllCarsUseCase()
                .onEach {
                    println("event is intercepted ")
                }
                .catch { e: Throwable ->
                    if (e is IllegalArgumentException)
                        emit(
                            listOf(
                                Car("111--A", 20, "Symbole", "Renault"),
                                Car("111--A", 20, "Symbole", "Renault"),
                                Car("111--A", 20, "Symbole", "Renault"),
                            )
                        )
                }.collect { cars ->
                    cars.mapToCarUiModelList()
                        .let {
                            _allCars.value = it
                        }
                }
            supervisorJob.complete()
        }
    }

    private fun List<Car>.mapToCarUiModelList(): List<CarUiModel> =
        map { CarUiModel(it.id ?: "", it.name ?: "") }


    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancel()
    }
}
