package com.e.cleanarchitecture.data

data class Shipment(val id: Int, val sender: String, val receiver: String, val address: String, val zipCode: Int)


sealed class Mode {
    object Road: Mode()
    object Air: Mode()
    object NotSupported : Mode()
}