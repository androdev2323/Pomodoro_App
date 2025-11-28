package com.example.pomodoro.presentation.BottomSheet.Components

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.LayoutInfo
import androidx.compose.ui.viewinterop.AndroidView
import com.example.pomodoro.R

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CustomNumberPicker(
    selectedValue:Int,
    list:List<Int>,
    textColor:Int = MaterialTheme.colorScheme.onBackground.toArgb(),
    onValueChange:(Int) -> Unit
){
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = {
            context ->
           val view= LayoutInflater.from(context).inflate(
               R.layout.numberpicker,
               null,
               false

           )
            val numberPicker = view.findViewById<NumberPicker>(R.id.numberPicker)
            numberPicker.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )


            numberPicker.minValue = list.first()
            numberPicker.maxValue = list.last()
            numberPicker.value = selectedValue
            numberPicker.textSize= 48f

            numberPicker.setOnValueChangedListener { numberPicker, oldVal, newVal ->
                onValueChange(newVal)
            }
            numberPicker.dividerPadding =   16
            numberPicker.textColor = textColor
            numberPicker
        },
        update = {
            it.minValue = list.first()
            it.maxValue = list.last()
            it.value = selectedValue
        }
    )
}