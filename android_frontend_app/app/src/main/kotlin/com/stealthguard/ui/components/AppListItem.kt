package com.stealthguard.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stealthguard.domain.models.AppInfo
import com.stealthguard.R
import com.stealthguard.util.toBitmap

/**
 * PUBLIC_INTERFACE
 * App row with icon, name and hide/lock toggles.
 */
@Composable
fun AppListItem(
    info: AppInfo,
    onToggleHide: (Boolean) -> Unit,
    onToggleLock: (Boolean) -> Unit,
    onLaunch: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            val bmp = info.icon?.let {
                try { it.toBitmap() } catch (_: Exception) { null }
            }
            if (bmp != null) {
                Image(bmp.asImageBitmap(), contentDescription = info.appName, modifier = Modifier.size(40.dp))
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(info.appName, style = MaterialTheme.typography.titleLarge)
                Text(info.packageName, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Hide")
                Switch(checked = info.isHidden, onCheckedChange = onToggleHide)
            }
            Spacer(Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Lock")
                Switch(checked = info.isLocked, onCheckedChange = onToggleLock)
            }
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onLaunch) {
                Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Open")
            }
        }
    }
}
