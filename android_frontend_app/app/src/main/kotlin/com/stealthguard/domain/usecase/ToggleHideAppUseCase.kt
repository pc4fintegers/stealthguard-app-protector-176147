package com.stealthguard.domain.usecase

import com.stealthguard.data.repository.AppRepository
/**
 * PUBLIC_INTERFACE
 * Toggles hidden state of a package.
 */
class ToggleHideAppUseCase(
    private val repo: AppRepository
) {
    suspend operator fun invoke(pkg: String, hide: Boolean) = repo.toggleHidden(pkg, hide)
}
