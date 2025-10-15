package com.stealthguard.ui.screens.scheduler

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * PUBLIC_INTERFACE
 * Simple scheduler screen to trigger a lock in 1 minute for a sample package.
 */
@Composable
fun TimerSchedulerScreen(nav: NavController, vm: TimerSchedulerViewModel) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Timer Scheduler")
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            val whenMs = System.currentTimeMillis() + 60_000
            vm.scheduleLock("com.example.someapp", whenMs)
        }) {
            Text("Lock com.example.someapp in 1 minute")
        }
    }
}
