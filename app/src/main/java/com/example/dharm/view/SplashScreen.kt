package com.example.dharm.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dharm.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay



@DelicateCoroutinesApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(1000)
        navController.popBackStack()
        navController.navigate("Chapters")
    }
    Box(modifier = Modifier.fillMaxSize().background(color = Color.Black), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.spritual_logo),
            contentDescription = "Splash Screen",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}