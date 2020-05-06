package com.e.cleanarchitecture.domain

import androidx.annotation.VisibleForTesting
import com.e.cleanarchitecture.data.*
import java.lang.Exception

class DeliveryHub : DeliveryHubUseCase {

    private val deliveryMap = HashMap<Int, Mode>().apply {
        put(1, Mode.Road)
        put(2, Mode.Road)
        put(3, Mode.Road)
        put(4, Mode.Road)
        put(5, Mode.Road)
        put(6, Mode.Air)
        put(7, Mode.Air)
        put(8, Mode.Air)
        put(9, Mode.Air)
        put(10, Mode.Air)

    }

    private var airConnectionRepository = AirConnectionRepository()
    private var roadConnectionRepository = RoadConnectionRepository()


    override fun connectShipment(shipment: Shipment, callback: Callback) {
        val mode = getMode(shipment)
        when (mode) {
            is Mode.Air ->
                airConnectionRepository.execute(shipment, object : Repository.Callback<Shipment> {
                    override fun success(value: Shipment) {
                        callback.success(value)
                    }

                    override fun error(value: Shipment, throwable: Throwable) {
                        callback.error(value, throwable)
                    }
                })
            is Mode.Road ->
                roadConnectionRepository.execute(shipment, object : Repository.Callback<Shipment> {
                    override fun success(value: Shipment) {
                        callback.success(value)
                    }

                    override fun error(value: Shipment, throwable: Throwable) {
                        callback.error(value, throwable)
                    }
                })
            is Mode.NotSupported -> {
                callback.error(shipment, NotSupport())
            }
        }


    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getMode(shipment: Shipment): Mode =
        deliveryMap[shipment.zipCode] ?: Mode.NotSupported

    interface Callback {
        fun success(shipment: Shipment)
        fun error(shipment: Shipment, throwable: Throwable)
    }

    class NotSupport : Exception("Not Support location")
}