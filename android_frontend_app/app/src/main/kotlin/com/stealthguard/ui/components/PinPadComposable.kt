package com.stealthguard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * PUBLIC_INTERFACE
 * Numeric PIN pad with callback for submit and delete.
 */
@Composable
fun PinPad(
    modifier: Modifier = Modifier,
    onSubmit: (String) -> Unit
) {
    var pin by remember { mutableStateOf("") }
    val keys = listOf("1","2","3","4","5","6","7","8","9","⌫","0","OK")
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter PIN: ${"*".repeat(pin.length)}", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        for (row in keys.chunked(3)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { key ->
                    Button(
                        onClick = {
                            when (key) {
                                "OK" -> onSubmit(pin)
                                "⌫" -> if (pin.isNotEmpty()) pin = pin.dropLast(1)
                                else -> if (pin.length < 8) pin += key
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .padding(4.dp)
                            .width(80.dp)
                    ) {
                        Text(key, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}
