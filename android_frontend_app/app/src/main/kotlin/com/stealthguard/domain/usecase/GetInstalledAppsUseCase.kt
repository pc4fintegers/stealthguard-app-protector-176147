package com.stealthguard.domain.usecase

import com.stealthguard.data.repository.AppRepository
import com.stealthguard.domain.models.AppInfo
import kotlinx.coroutines.flow.Flow
/**
 * PUBLIC_INTERFACE
 * Returns a flow of installed apps merged with hidden/locked flags.
 */
class GetInstalledAppsUseCase(
    private val repo: AppRepository
) {
    operator fun invoke(): Flow<List<AppInfo>> = repo.observeInstalledApps()
}
