package com.e.cleanarchitecture.domain

import com.e.cleanarchitecture.data.Mode
import com.e.cleanarchitecture.data.Shipment
import org.junit.Test

class DeliveryHubUseCaseTest {


    val sut = DeliveryHub()

    @Test
    fun getMode_WithZipcode7_ReturnAir() {
        val mode = sut.getMode(Shipment(1, "", "", "", 7))
        assert(mode is Mode.Air)
    }

    @Test
    fun getMode_WithZipcode1_ReturnRoad() {
        val mode = sut.getMode(Shipment(1, "", "", "", 1))
        assert(mode is Mode.Road)
    }


    @Test
    fun getMode_WithZipcode11_ReturnNotSupported() {
        val mode = sut.getMode(Shipment(1, "", "", "", 11))
        assert(mode is Mode.NotSupported)
    }

    @Test
    fun connectShipment_WithZipcode1_InterceptSuccess() {
        sut.connectShipment(Shipment(1, "", "", "", 1), object : DeliveryHub.Callback {
            override fun success(shipment: Shipment) {
                assert(true)
            }

            override fun error(shipment: Shipment, throwable: Throwable) {
                assert(false)
            }

        })
    }

    @Test
    fun connectShipment_WithZipcode11_InterceptError() {
        sut.connectShipment(Shipment(1, "", "", "", 11), object : DeliveryHub.Callback {
            override fun success(shipment: Shipment) {
                assert(false)
            }

            override fun error(shipment: Shipment, throwable: Throwable) {
                assert(true)
            }

        })
    }
}