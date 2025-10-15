package com.stealthguard.data.repository

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.stealthguard.data.db.dao.HiddenAppDao
import com.stealthguard.data.db.dao.LockRuleDao
import com.stealthguard.data.db.dao.LockedAppDao
import com.stealthguard.data.db.entities.HiddenApp
import com.stealthguard.data.db.entities.LockRule
import com.stealthguard.data.db.entities.LockedApp
import com.stealthguard.data.prefs.SecurePrefs
import com.stealthguard.domain.models.AppInfo
import com.stealthguard.timer.Scheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

/**
 * PUBLIC_INTERFACE
 * Repository merging installed apps with DB state, and handling PIN and rules.
 */
class AppRepository(
    private val context: Context,
    private val pm: PackageManager,
    private val hiddenDao: HiddenAppDao,
    private val lockedDao: LockedAppDao,
    private val ruleDao: LockRuleDao,
    private val securePrefs: SecurePrefs,
    private val scheduler: Scheduler
) {

    fun observeInstalledApps(): Flow<List<AppInfo>> {
        val installedFlow = flow {
            val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            val list = pm.queryIntentActivities(mainIntent, 0)
                .map {
                    val appName = it.loadLabel(pm).toString()
                    val pkg = it.activityInfo.packageName
                    val icon: Drawable? = try { it.loadIcon(pm) } catch (_: Exception) { null }
                    Triple(appName, pkg, icon)
                }
            emit(list)
        }
        val hiddenFlow = hiddenDao.observeAll()
        val lockedFlow = lockedDao.observeAll()
        return combine(installedFlow, hiddenFlow, lockedFlow) { installed, hidden, locked ->
            val hiddenSet = hidden.map { it.packageName }.toSet()
            val lockedSet = locked.map { it.packageName }.toSet()
            installed.map { (name, pkg, icon) ->
                AppInfo(
                    appName = name,
                    packageName = pkg,
                    icon = icon,
                    isHidden = hiddenSet.contains(pkg),
                    isLocked = lockedSet.contains(pkg)
                )
            }.sortedBy { it.appName.lowercase() }
        }
    }

    suspend fun toggleHidden(pkg: String, hide: Boolean) {
        if (hide) hiddenDao.insert(HiddenApp(pkg)) else hiddenDao.delete(HiddenApp(pkg))
    }

    suspend fun toggleLocked(pkg: String, lock: Boolean) {
        if (lock) lockedDao.insert(LockedApp(pkg)) else lockedDao.delete(LockedApp(pkg))
    }

    fun hasPin(): Boolean = securePrefs.hasPin()
    fun setPin(pin: String) = securePrefs.setPin(pin)
    fun validatePin(pin: String): Boolean = securePrefs.validate(pin)

    suspend fun addRule(rule: LockRule): Long {
        val id = ruleDao.insert(rule)
        scheduler.schedule(rule.copy(id = id))
        return id
    }

    suspend fun removeRule(rule: LockRule) {
        ruleDao.delete(rule)
        scheduler.cancel(rule)
    }

    fun context() = context
}
