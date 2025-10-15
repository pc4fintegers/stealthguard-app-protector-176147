package com.stealthguard.ui.screens.scheduler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealthguard.data.db.entities.LockRule
import com.stealthguard.Injector
import com.stealthguard.AppContext
import kotlinx.coroutines.launch

/**
 * PUBLIC_INTERFACE
 * VM for scheduling lock/hide rules.
 */
class TimerSchedulerViewModel : ViewModel() {
    private val schedule = Injector.scheduleRule(AppContext.app)
    fun scheduleLock(pkg: String, triggerAtMillis: Long) = viewModelScope.launch {
        schedule(LockRule(packageName = pkg, action = "LOCK", triggerAtMillis = triggerAtMillis))
    }
}
