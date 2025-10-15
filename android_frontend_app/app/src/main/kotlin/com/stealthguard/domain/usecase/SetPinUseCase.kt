package com.stealthguard.domain.usecase

import com.stealthguard.data.repository.AppRepository
/**
 * PUBLIC_INTERFACE
 * Sets or updates the PIN in secure storage.
 */
class SetPinUseCase(
    private val repo: AppRepository
) {
    operator fun invoke(pin: String) = repo.setPin(pin)
}
