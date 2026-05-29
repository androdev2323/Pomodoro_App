package com.example.pomodoro.presentation.TaskEditScreen.Components


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pomodoro.presentation.TaskEditScreen.Uistate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun AnimatedButton(
    uiState: Uistate,
    onClick: () -> Unit,
    afterAnimation: () -> Unit
) {
    val transition = updateTransition(
        targetState = uiState,
        label = "ButtonTransition"
    )

    var buttonWidthPx by remember { mutableStateOf(0f) }
    val density = LocalDensity.current
    val targetWidthDp = with(density) { buttonWidthPx.toDp() }


    val animatedWidth by transition.animateDp(
        transitionSpec = {
            tween(durationMillis = 1000)
        },
        label = "WidthAnimation"
    ) { state ->
        Log.d("ganesh",state.javaClass.simpleName)
        when (state) {
            is Uistate.Success -> targetWidthDp
            else -> targetWidthDp * 20
        }
    }


    LaunchedEffect(uiState) {
        if (uiState is Uistate.Success) {

                delay(3000)
            withContext(Dispatchers.Main) {
                afterAnimation()
            }
        }
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .width(200.dp)
            .height(50.dp)
            .onGloballyPositioned {
                buttonWidthPx = it.size.width.toFloat()
            },
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(10.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
        ) {


            transition.AnimatedVisibility(
                visible = { it is Uistate.Success },
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 1500)) { fullWidth -> -fullWidth },
                exit = slideOutHorizontally(
                    animationSpec = tween(1500)
                ) { it })
            {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(animatedWidth)
                        .background(Color.Green.copy(alpha = 0.6f)),

                    ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Success",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }



            if (uiState is Uistate.Loading) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    CircularProgressIndicator(

                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }


            if (uiState is Uistate.Idle || uiState is Uistate.Error) {
                transition.AnimatedVisibility(
                    visible = { it is Uistate.Idle },
                    enter = slideInHorizontally(animationSpec = tween(durationMillis = 1500)) { fullWidth -> -fullWidth },
                ) {
                    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        Text(
                            text = "Save",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}
