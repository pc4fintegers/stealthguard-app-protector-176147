package com.stealthguard.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealthguard.domain.models.AppInfo
import com.stealthguard.Injector
import com.stealthguard.AppContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * PUBLIC_INTERFACE
 * ViewModel for the home screen.
 */
class HomeViewModel : ViewModel() {

    private val getInstalled = Injector.getInstalledApps(AppContext.app)
    private val toggleHide = Injector.toggleHide(AppContext.app)
    private val toggleLock = Injector.toggleLock(AppContext.app)

    val apps: StateFlow<List<AppInfo>> = getInstalled()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun toggleHidden(pkg: String, hide: Boolean) = viewModelScope.launch {
        toggleHide(pkg, hide)
    }

    fun toggleLocked(pkg: String, lock: Boolean) = viewModelScope.launch {
        toggleLock(pkg, lock)
    }
}
