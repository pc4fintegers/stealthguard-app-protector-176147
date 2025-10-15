package com.stealthguard.domain.usecase

import com.stealthguard.data.repository.AppRepository
/**
 * PUBLIC_INTERFACE
 * Validates user-entered PIN.
 */
class ValidatePinUseCase(
    private val repo: AppRepository
) {
    operator fun invoke(pin: String): Boolean = repo.validatePin(pin)
}
