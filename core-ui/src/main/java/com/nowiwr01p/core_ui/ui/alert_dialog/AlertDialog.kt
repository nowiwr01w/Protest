package com.nowiwr01p.core_ui.ui.alert_dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.nowiwr01p.core_ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.message
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title

@Composable
fun CustomAlertDialog(
    title: String,
    description: String,
    positiveText: String = "Да",
    negativeText: String = "Нет",
    positiveCallback: () -> Unit = {},
    negativeCallback: () -> Unit = {}
) {
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        shape = RoundedCornerShape(16.dp),
        buttons = {
            positiveButton(
                text = positiveText,
                textStyle = MaterialTheme.typography.alertButton.copy(
                    color = MaterialTheme.colors.mainBackgroundColor
                ),
                onClick = positiveCallback
            )
            positiveButton(
                text = negativeText,
                textStyle = MaterialTheme.typography.alertButton.copy(
                    color = MaterialTheme.colors.textNegative
                ),
                onClick = negativeCallback
            )
        }
    ) {
        title(
            text = title,
            color = MaterialTheme.colors.textPrimary,
            style = MaterialTheme.typography.subtitle1,
        )
        message(
            text = description,
            color = MaterialTheme.colors.textColorSecondary,
            style = MaterialTheme.typography.body2,
        )
    }
    dialogState.show()
}