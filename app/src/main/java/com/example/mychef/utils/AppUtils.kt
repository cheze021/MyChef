package com.example.mychef.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties


@Composable
fun GenericAlertDialog(
    title: String,
    message: String,
    icon: ImageVector,
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    isFavorite: Boolean
) {
    AlertDialog(
        shape = RoundedCornerShape(24.dp),
        containerColor = Color(0xFFFDFDFD),
        onDismissRequest = {
            if (dismissOnClickOutside) onDismiss()
        },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = if(isFavorite) Color(0xFF4CAF50) else Color(0xFFE57373),
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = Color(0xFF212121),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center
                )
            }
        },
        dismissButton = {
            StyledDismissButton(onDismiss, dismissText)
        },
        confirmButton = {
            if(isFavorite) StyledConfirmButton(onConfirm, confirmText)
        },
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    )
}


@Composable
fun StyledConfirmButton(
    onConfirm: () -> Unit,
    confirmText: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Button(
            onClick = onConfirm,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF7F0F0),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = confirmText,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun StyledDismissButton(
    onDismiss: () -> Unit,
    dismissText: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE8AEB1),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = dismissText,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}