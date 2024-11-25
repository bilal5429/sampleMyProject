package com.tcs.vehicle.ui.view.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcs.vehicle.domain.usecase.getcars.GetCarsUseCase
import com.tcs.vehicle.domain.util.CarOrder
import com.tcs.vehicle.domain.util.OrderType
import com.tcs.data.common.Resource
import com.tcs.data.model.CarSearchResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val carsUseCase: GetCarsUseCase,
) : ViewModel() {

    val state = mutableStateOf<CarsListUiState>(Loading)
    private var selectedOrder: CarOrder = CarOrder.Date(OrderType.Descending)
    private var _query: String = ""

    init {
        getCars()
    }

    fun searchCars(query: String) = viewModelScope.launch {
        _query = query
        carsUseCase(_query, selectedOrder).collect {
            state.value = CarsListUiStateReady(cars = it)
        }
    }

    fun filterCars(filter: Boolean) = viewModelScope.launch {
        selectedOrder = CarOrder.Price(if (filter) OrderType.Descending else OrderType.Ascending)
        carsUseCase(_query, selectedOrder).collect {
            state.value = CarsListUiStateReady(cars = it)
        }
    }

    fun getCars() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            carsUseCase().collect(::handleResponse)
        }
    }

    private suspend fun handleResponse(it: Resource<List<CarSearchResponseItem>>) = withContext(Dispatchers.Main) {
        when (it.status) {
            Resource.Status.LOADING -> state.value = Loading
            Resource.Status.SUCCESS -> state.value = CarsListUiStateReady(cars = it.data)
            Resource.Status.ERROR -> state.value =
                CarsListUiStateError(error = it.error?.data?.message)
        }
    }
}

sealed class CarsListUiState
data class CarsListUiStateReady(val cars: List<CarSearchResponseItem>?) : CarsListUiState()
object Loading : CarsListUiState()
class CarsListUiStateError(val error: String? = null) : CarsListUiState()