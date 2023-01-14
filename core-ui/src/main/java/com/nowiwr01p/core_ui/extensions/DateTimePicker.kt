package com.nowiwr01p.core_ui.extensions

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import com.nowiwr01p.core.extenstion.formatToDate
import com.nowiwr01p.core.extenstion.formatToTime
import com.nowiwr01p.core_ui.theme.backgroundSecondary
import com.nowiwr01p.core_ui.theme.mainBackgroundColor
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

interface DatePickerListener {
    fun onDateSelected(date: String)
}

@Composable
fun DatePicker(listener: DatePickerListener) {
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        buttons = {
            positiveButton(
                text = "OK",
                textStyle = MaterialTheme.typography.button.copy(
                    color = MaterialTheme.colors.mainBackgroundColor,
                )
            )
        }
    ) {
        datepicker(
            yearRange = (2023..2025),
            colors = DatePickerDefaults.colors(
                headerTextColor = Color.White,
                headerBackgroundColor = MaterialTheme.colors.mainBackgroundColor,
                dateActiveBackgroundColor = Color(0xFFFC4C4C)
            )
        ) { date ->
            val resultDate = date.formatToDate()
            listener.onDateSelected(resultDate)
        }
    }
    dialogState.show()
}

interface TimePickerListener {
    fun onTimeSelected(time: String)
}

@Composable
fun TimePicker(listener: TimePickerListener) {
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        buttons = {
            positiveButton(
                text = "OK",
                textStyle = MaterialTheme.typography.button.copy(
                    color = MaterialTheme.colors.mainBackgroundColor,
                )
            )
        }
    ) {
        timepicker(
            is24HourClock = true,
            colors = TimePickerDefaults.colors(
                headerTextColor = Color.White,
                borderColor = MaterialTheme.colors.mainBackgroundColor,
                activeBackgroundColor = MaterialTheme.colors.mainBackgroundColor,
                inactiveBackgroundColor = MaterialTheme.colors.backgroundSecondary,
                selectorColor = Color(0xFFFC4C4C)
            )
        ) { time ->
            val resultTime = time.formatToTime()
            listener.onTimeSelected(resultTime)
        }
    }
    dialogState.show()
}