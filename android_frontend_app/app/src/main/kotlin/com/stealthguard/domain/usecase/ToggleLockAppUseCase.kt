package com.stealthguard.domain.usecase

import com.stealthguard.data.repository.AppRepository
/**
 * PUBLIC_INTERFACE
 * Toggles lock state of a package.
 */
class ToggleLockAppUseCase(
    private val repo: AppRepository
) {
    suspend operator fun invoke(pkg: String, lock: Boolean) = repo.toggleLocked(pkg, lock)
}
