package com.example.dharm.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dharm.view.Chapters
import com.example.dharm.view.Verse
import com.example.dharm.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ChapterConnectivityStatus(navController: NavController) {
    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
        Chapters(navController)
    } else {
        NoInternetScreen()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun VerseConnectivityStatus(
    navController: NavController,
    chapterNumber: Int,
    chapterTitle: String,
    chapterSummary: String,
    viewModel: MainViewModel,
    verseCount: String
) {
    val connection by connectivityState()

    if (connection === ConnectionState.Available) {
        Verse(
            navController = navController,
            chapterNumber = chapterNumber,
            chapterTitle = chapterTitle,
            chapterSummary = chapterSummary,
            viewModel = viewModel,
            verseCount = verseCount
        )
    } else {
        NoInternetScreen()
    }
}
