package com.e.cleanarchitecture.presentation

import com.e.cleanarchitecture.domain.DeliveryHub
import com.e.cleanarchitecture.domain.DeliveryHubUseCase

fun getDeliveryHub() : DeliveryHubUseCase = DeliveryHub()