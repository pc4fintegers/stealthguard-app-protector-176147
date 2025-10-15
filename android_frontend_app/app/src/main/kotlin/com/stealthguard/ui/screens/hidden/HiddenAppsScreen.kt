package com.stealthguard.ui.screens.hidden

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

/**
 * PUBLIC_INTERFACE
 * Screen listing hidden apps.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HiddenAppsScreen(nav: NavController, vm: HiddenAppsViewModel) {
    val items = vm.hidden.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text("Hidden Apps") }) }) { padding ->
        Column(Modifier.fillMaxSize()) {
            LazyColumn(contentPadding = padding) {
                items(items.value) {
                    Text(it.appName)
                }
            }
        }
    }
}
