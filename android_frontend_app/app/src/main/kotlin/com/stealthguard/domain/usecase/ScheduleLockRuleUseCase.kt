package com.stealthguard.domain.usecase

import com.stealthguard.data.db.entities.LockRule
import com.stealthguard.data.repository.AppRepository
/**
 * PUBLIC_INTERFACE
 * Adds a new schedule rule and registers it with the scheduler.
 */
class ScheduleLockRuleUseCase(
    private val repo: AppRepository
) {
    suspend operator fun invoke(rule: LockRule) = repo.addRule(rule)
}
