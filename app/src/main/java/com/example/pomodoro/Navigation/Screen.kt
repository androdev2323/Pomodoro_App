package com.example.pomodoro.Navigation

import android.media.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppBlocking
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AppBlocking
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.pomodoro.R
import kotlinx.serialization.Serializable


@Serializable
sealed interface HomeScreenRoutes {

    @Serializable
    data object Home : HomeScreenRoutes


    @Serializable
    data class Stopwatch(val id: Int) : HomeScreenRoutes {

    }
}

@Serializable
sealed interface PomodoroRoutes {
    @Serializable
    data object home:PomodoroRoutes

    @Serializable
    data object appblock:PomodoroRoutes

    @Serializable
    data object about:PomodoroRoutes

    companion object{
        val mainroutes = listOf(
            BottomNavigationItem(
                title = "Home",
                routes = PomodoroRoutes.home,
                selectedImage = Icons.Filled.Home,
                unselectedImage = Icons.Outlined.Home
            ),
            BottomNavigationItem(
                title = "AppBlock",
                routes = PomodoroRoutes.appblock,
                selectedImage = Icons.Filled.AppBlocking,
                unselectedImage = Icons.Outlined.AppBlocking
            ),
            BottomNavigationItem(
                title = "About",
                routes = PomodoroRoutes.about,
                selectedImage = Icons.Filled.Info,
                unselectedImage = Icons.Outlined.Info
            )
              
        )
    }
}

data class BottomNavigationItem(
    val title:String,
    val routes: PomodoroRoutes,
    val selectedImage:ImageVector,
    val unselectedImage:ImageVector,

){
    val routeClassname:String
        get() = routes::class.qualifiedName?: ""
}