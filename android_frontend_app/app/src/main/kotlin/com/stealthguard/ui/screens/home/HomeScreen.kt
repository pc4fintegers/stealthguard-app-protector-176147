package com.stealthguard.ui.screens.home

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stealthguard.domain.models.AppInfo
import com.stealthguard.ui.components.AppListItem
import com.stealthguard.ui.navigation.Routes
import com.stealthguard.ui.theme.StealthGuardTheme

/**
 * PUBLIC_INTERFACE
 * Home screen showing installed apps and toggles.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav: NavController, vm: HomeViewModel) {
    val apps by vm.apps.collectAsState()
    val ctx = LocalContext.current
    StealthGuardTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("StealthGuard") })
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = { Icon(Icons.Default.VisibilityOff, contentDescription = null) },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { nav.navigate(Routes.HIDDEN) },
                        icon = { Icon(Icons.Default.VisibilityOff, contentDescription = null) },
                        label = { Text("Hidden") }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { nav.navigate(Routes.PASSWORD) },
                        icon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        label = { Text("Password") }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { nav.navigate(Routes.SCHEDULER) },
                        icon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                        label = { Text("Timer") }
                    )
                }
            }
        ) { padding ->
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(12.dp)
            ) {
                items(apps, key = { it.packageName }) { app ->
                    Spacer(Modifier.height(8.dp))
                    AppListItem(
                        info = app,
                        onToggleHide = { vm.toggleHidden(app.packageName, it) },
                        onToggleLock = { vm.toggleLocked(app.packageName, it) },
                        onLaunch = { launchApp(ctx.packageManager, app) }
                    )
                }
                item { Spacer(Modifier.height(64.dp)) }
            }
        }
    }
}

private fun launchApp(pm: android.content.pm.PackageManager, info: AppInfo) {
    val intent = pm.getLaunchIntentForPackage(info.packageName)
    intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    if (intent != null) {
        try {
            android.app.Application().startActivity(intent)
        } catch (_: Exception) { }
    }
}
