package com.example.pomodoro.presentation.StopWatch.Components

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pomodoro.R
import com.example.pomodoro.ui.theme.PomodoroTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StopwatchTimerComponent(
    modifier: Modifier = Modifier,
    time: String,
    progress: Long,
    animationid:Int

    ) {
    val composition: LottieComposition? by  rememberLottieComposition( LottieCompositionSpec.RawRes(
        animationid
    ))
    val lottieanimation by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    mapColors()
    val progressvalue = animateFloatAsState(targetValue = progress.toFloat(),
        animationSpec = tween(durationMillis = 1000, delayMillis = 0, easing = LinearEasing)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .drawWithCache {


                val primarycolor = MappedColors.primary
                val secondarycolor = MappedColors.secondary

                val centerX = size.width / 2f
                val centerY = size.height / 2f
                val strokeWidth = size.width / 30f
                val radius = (size.minDimension / 2.0f) - strokeWidth - 15.dp.toPx()

                // Pre-calculate tick positions once
                val tickStartRadius = radius + strokeWidth / 2f + 5.dp.toPx()
                val tickEndRadius = tickStartRadius + 8.dp.toPx()

                val tickPoints = (0 until 100).map { i ->
                    val angleInDegrees = i * 3.6
                    val angleInRadians = Math.toRadians(angleInDegrees)

                    val start = Offset(
                        x = (centerX + tickStartRadius * cos(angleInRadians)).toFloat(),
                        y = (centerY + tickStartRadius * sin(angleInRadians)).toFloat()
                    )
                    val end = Offset(
                        x = (centerX + tickEndRadius * cos(angleInRadians)).toFloat(),
                        y = (centerY + tickEndRadius * sin(angleInRadians)).toFloat()
                    )
                    start to end // Return a Pair of Offsets
                }

                val gradientBrush = Brush.radialGradient(
                    listOf(
                        primarycolor.copy(alpha = 0.70f),
                        secondarycolor.copy(alpha = 0.15f)
                    )
                )

                onDrawBehind {

                    drawCircle(
                        brush = gradientBrush,
                        center = center,
                        radius = radius,
                    )


                    drawCircle(
                        color = secondarycolor.copy(alpha = 0.6f),
                        center = center,
                        radius = radius,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round
                        )
                    )


                    drawArc(
                        color = primarycolor,
                        startAngle = -90f,
                        sweepAngle = (360f / 100) * progressvalue.value,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                        topLeft = Offset(x = centerX - radius, y = centerY - radius),
                        size = Size(width = radius * 2f, height = radius * 2f)
                    )


                    rotate(degrees = -90f, pivot = center) {
                        tickPoints.forEachIndexed { i, (start, end) ->
                            val color =
                                if (i < progressvalue.value) primarycolor else primarycolor.copy(alpha = 0.3f)
                            drawLine(
                                color = color,
                                start = start,
                                end = end,
                                strokeWidth = 2.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally){
           Spacer(modifier = Modifier.height(30.dp))

        LottieAnimation(modifier = Modifier.size(200.dp), composition = composition, progress = { lottieanimation })
        Text(
            modifier = Modifier.weight(1f),
            text = time,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
        }
}


@Preview(showBackground = true)
@Composable
fun PreviewStopWatchTimerComponent() {
    PomodoroTheme {


        StopwatchTimerComponent(Modifier.size(350.dp), "20:24", animationid = R.raw.breaktime2, progress = 50)
    }
}

private object MappedColors {
    var primary: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified
    var secondary: androidx.compose.ui.graphics.Color =
        androidx.compose.ui.graphics.Color.Unspecified
}

@Composable
private fun mapColors() {
    MappedColors.primary = MaterialTheme.colorScheme.primary
    MappedColors.secondary = MaterialTheme.colorScheme.secondary
}


