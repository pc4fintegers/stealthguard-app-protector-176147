package com.stealthguard.ui.screens.password

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stealthguard.ui.components.PinPad

/**
 * PUBLIC_INTERFACE
 * Screen to set or change PIN.
 */
@Composable
fun PasswordScreen(nav: NavController, vm: PasswordViewModel) {
    val snack = remember { SnackbarHostState() }
    var message by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Setup or Change PIN", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        PinPad(onSubmit = { pin ->
            vm.set(pin)
            message = "PIN updated"
        })
        Spacer(Modifier.height(12.dp))
        Button(onClick = { nav.popBackStack() }) { Text("Back") }
        SnackbarHost(hostState = snack)
        if (message.isNotEmpty()) {
            LaunchedEffect(message) {
                snack.showSnackbar(message)
                message = ""
            }
        }
    }
}
