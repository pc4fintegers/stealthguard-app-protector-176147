package com.stealthguard.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.stealthguard.ui.navigation.AppNavHost
import com.stealthguard.ui.theme.StealthGuardTheme

/**
 * PUBLIC_INTERFACE
 * Custom launcher activity hosting the app's navigation in Compose.
 */
class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StealthGuardTheme {
                val nav = rememberNavController()
                AppNavHost(navController = nav)
            }
        }
    }
}
