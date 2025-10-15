package com.stealthguard.ui.screens.hidden

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealthguard.domain.models.AppInfo
import com.stealthguard.Injector
import com.stealthguard.AppContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * PUBLIC_INTERFACE
 * VM for hidden apps.
 */
class HiddenAppsViewModel : ViewModel() {
    private val getInstalled = Injector.getInstalledApps(AppContext.app)
    val hidden: StateFlow<List<AppInfo>> = getInstalled()
        .map { list -> list.filter { a -> a.isHidden } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
