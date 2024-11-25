package com.tcs.vehicle.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
