package com.stealthguard.ui.screens.password

import androidx.lifecycle.ViewModel
import com.stealthguard.Injector
import com.stealthguard.AppContext

/**
 * PUBLIC_INTERFACE
 * VM for password setup and change.
 */
class PasswordViewModel : ViewModel() {
    private val setPinUC = Injector.setPin(AppContext.app)
    private val validateUC = Injector.validatePin(AppContext.app)

    fun set(pin: String) = setPinUC(pin)
    fun validate(pin: String): Boolean = validateUC.invoke(pin)
}
