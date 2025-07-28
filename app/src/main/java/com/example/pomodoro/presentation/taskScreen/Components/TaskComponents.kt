package com.example.pomodoro.presentation.taskScreen.Components

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import com.example.pomodoro.ui.theme.PomodoroTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Composable
fun TaskItemCard(
    modifier: Modifier = Modifier,
    progress: String,
    onClick: () -> Unit,
    taskTitle: String,
    taskDesc: Int
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .padding(5.dp)
            .fillMaxWidth(),

        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                BoxWithConstraints(
                    modifier = modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                ) {
                    TaskCircularProgress(size = this.maxHeight, percentage = progress.toFloat())
                }
                TaskDetailElement(modifier = Modifier.weight(1f), taskTitle, taskDesc)
            }


            if (progress.toInt() >= 100) {
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Green,
                                    Color.Green.copy(alpha = 0.2f)
                                )
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun TaskCircularProgress(modifier: Modifier = Modifier, size: Dp, percentage: Float) {
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        val progress = animateFloatAsState(
            targetValue = percentage / 100f,
            tween(durationMillis = 400, easing = FastOutLinearInEasing)
        )


        CircularProgressIndicator(
            progress = {
                progress.value
            },
            modifier = Modifier
                .fillMaxSize()

                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 5.dp,
            strokeCap = StrokeCap.Round,
            trackColor = MaterialTheme.colorScheme.tertiary,

            )
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)){
            Text(
                text = "${(progress.value * 100).toInt()} %",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall
            )
            if (progress.value == 1f) {
                Text(
                    text = "Done",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun TaskDetailElement(modifier: Modifier = Modifier, taskTitle: String, taskDuration:Int ) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = taskTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,

                )

            Box(
                modifier = Modifier
                    .background(color = Color.Green, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    "Done",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }

        }

        Text(text= "Duration: $taskDuration hrs", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)


    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Preview
@Composable
private fun TaskItemCardPreview() {
    Scaffold {
        PomodoroTheme {


            TaskItemCard(
                modifier = Modifier.padding(it),
                progress = "100",
                onClick = { /*TODO*/ },
                taskTitle = "Android App Development",
                taskDesc = 2
            )
        }
    }
}