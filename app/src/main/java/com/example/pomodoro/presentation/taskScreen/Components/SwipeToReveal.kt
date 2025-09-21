package com.example.pomodoro.presentation.taskScreen.Components

import androidx.compose.ui.graphics.Color
import android.graphics.drawable.Icon
import androidx.appcompat.widget.TintInfo
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.pomodoro.Data.local.Entity.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun RowActions(
    onClick: () -> Unit,

    backgroundColor: Color,
    icon: ImageVector,
    tint: Color = Color.White
) {
    IconButton(
        onClick, modifier = Modifier
            .background(color = backgroundColor)

            .padding(10.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = tint)
    }
}


@Composable
fun SwipeToReveal(
    isfordelete: Boolean,
    isExpanded: Boolean,
    onExpanded: () -> Unit,
    onCollapsed: () -> Unit,
    actions: @Composable () -> Unit,
    content: @Composable () -> Unit,
    postDelete: () -> Unit,
    modifier: Modifier = Modifier
) {


    var rowwidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(initialValue = 0f) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(isfordelete) {
        if (isfordelete) {
            offset.animateTo(-rowwidth - 2000)
            delay(20)
            postDelete()
            onCollapsed()
        }
    }
  LaunchedEffect(isExpanded) {
      if(isExpanded){
          offset.animateTo(-rowwidth)
      }
      else{
          offset.animateTo(0f)
      }
  }
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            modifier = if (isfordelete) Modifier
                .clickable(false, onClick = {})
                .alpha(0f) else Modifier

                .onSizeChanged {
                    rowwidth = it.width.toFloat()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)

        ) {
            actions()

        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(offset.value.roundToInt(), 0)
                }
                .pointerInput(rowwidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { o_, dragAmount ->

                            scope.launch {
                                val newoffset = offset.value + dragAmount
                                offset.snapTo(newoffset.coerceIn(-rowwidth, 0f))
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                if ((offset.value) <= -rowwidth / 6f) {
                                    offset.animateTo(-rowwidth)
                                    onExpanded()

                                } else if (offset.value == 0f) {
                                    onCollapsed()
                                }
                            }
                        })

                }
        ) {

            content()
        }
    }

}