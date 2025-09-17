package com.example.pomodoro.presentation.taskScreen.Components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pomodoro.ui.theme.PomodoroTheme

@Composable
fun DailyTaskCard(
    modifier: Modifier = Modifier,
    totalTask: Int = 0,
    completedTask: Int = 0
) {

    val progress = if (totalTask > 0) {
        completedTask.toFloat() / totalTask.toFloat()
    } else {
        0f
    }


    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000),
        label = "progressAnimation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Daily Progress",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "$completedTask / $totalTask Tasks",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Indicator
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.tertiary,
                trackColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (totalTask > 0) {
                    Text(
                        text = "${(progress * 100).toInt()}% Completed",
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Text(
                        text = "Add tasks to get started!",
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

// --- PREVIEWS ---

@Preview(name = "In Progress")
@Composable
fun DailyTaskCardPreview() {
    PomodoroTheme {
        DailyTaskCard(totalTask = 8, completedTask = 5)
    }
}

@Preview(name = "Empty State")
@Composable
fun DailyTaskCardPreviewEmpty() {
    PomodoroTheme {
        DailyTaskCard(totalTask = 0, completedTask = 0)
    }
}

@Preview(name = "Completed")
@Composable
fun DailyTaskCardPreviewCompleted() {
    PomodoroTheme {
        DailyTaskCard(totalTask = 10, completedTask = 10)
    }
}

@Preview(name = "Dark Mode")
@Composable
fun DailyTaskCardPreviewDark() {
    PomodoroTheme(darkTheme = true) {
        DailyTaskCard(totalTask = 8, completedTask = 3)
    }
}