package com.c22ps099.relasiahelpseekerapp.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.view.MotionEvent
import android.widget.EditText
import java.util.*

@SuppressLint("ClickableViewAccessibility")
fun setDate(view: EditText, context: Context) {
    view.showSoftInputOnFocus = false
    view.setOnTouchListener { v, event ->
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, myear, mmonth, mdayOfMonth ->
                        val dd = if (mdayOfMonth > 10) {
                            mdayOfMonth
                        } else "0$mdayOfMonth"
                        val mm = if (mmonth > 10) {
                            mdayOfMonth
                        } else "0$mmonth"

                        view.setText("$dd/$mm/$myear")
                    }, year, month, day
                )
                datePickerDialog.show()
            }
        }

        v?.onTouchEvent(event) ?: true
    }
}