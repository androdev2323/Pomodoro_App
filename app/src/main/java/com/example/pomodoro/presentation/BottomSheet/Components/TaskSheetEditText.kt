package com.example.pomodoro.presentation.BottomSheet.Components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TaskEdittext(
    title: String,
    hint: String,
    value: String,
    onvalueChange: (String) -> Unit,
    errormessage: String? = null
) {
    Column() {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { onvalueChange(it) },
            placeholder = { Text(text = hint) },
            isError = errormessage != null,
            supportingText = {
                errormessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            )
        )
    }
}


@Composable
fun AnimatedProgressButton() {

    var isClicked by remember { mutableStateOf(false) }
    var buttonWidth by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current


    val animatedWidth by animateFloatAsState(
        targetValue = if (isClicked) buttonWidth else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "animatedWidth"
    )

    Button(
        onClick = {
            scope.launch {
                isClicked = true
                delay(2000)
                isClicked = false
            }
        },
        modifier = Modifier
            .padding(16.dp)
            .onGloballyPositioned { layout ->
                buttonWidth = layout.size.width.toFloat()
            },
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()

                .clip(RoundedCornerShape(10.dp))
        ) {


            if (animatedWidth > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(with(density){animatedWidth.toDp()} )
                        .background(Color.Green)
                )
            }


            Text(
                text = if (!isClicked) "Submit" else "Submitting...",

                color = Color.White
            )
        }
    }
}
