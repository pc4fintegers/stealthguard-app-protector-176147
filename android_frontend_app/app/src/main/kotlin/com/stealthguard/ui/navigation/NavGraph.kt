package com.stealthguard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stealthguard.ui.screens.hidden.HiddenAppsScreen
import com.stealthguard.ui.screens.hidden.HiddenAppsViewModel
import com.stealthguard.ui.screens.home.HomeScreen
import com.stealthguard.ui.screens.home.HomeViewModel
import com.stealthguard.ui.screens.password.PasswordScreen
import com.stealthguard.ui.screens.password.PasswordViewModel
import com.stealthguard.ui.screens.scheduler.TimerSchedulerScreen
import com.stealthguard.ui.screens.scheduler.TimerSchedulerViewModel

/**
 * PUBLIC_INTERFACE
 * Compose navigation setup.
 */
object Routes {
    const val HOME = "home"
    const val HIDDEN = "hidden"
    const val PASSWORD = "password"
    const val SCHEDULER = "scheduler"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            val vm: HomeViewModel = viewModel()
            HomeScreen(navController, vm)
        }
        composable(Routes.HIDDEN) {
            val vm: HiddenAppsViewModel = viewModel()
            HiddenAppsScreen(navController, vm)
        }
        composable(Routes.PASSWORD) {
            val vm: PasswordViewModel = viewModel()
            PasswordScreen(navController, vm)
        }
        composable(Routes.SCHEDULER) {
            val vm: TimerSchedulerViewModel = viewModel()
            TimerSchedulerScreen(navController, vm)
        }
    }
}
