package com.e.cleanarchitecture.domain

import com.e.cleanarchitecture.data.Shipment

interface DeliveryHubUseCase {
    fun connectShipment(shipment: Shipment, callback: DeliveryHub.Callback)
}