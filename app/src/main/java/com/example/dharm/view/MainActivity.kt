package com.example.dharm.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dharm.network.ChapterConnectivityStatus
import com.example.dharm.ui.theme.DharmTheme
import com.example.dharm.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DharmTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "Chapters") {
                    composable("Home") {

                        Home(navController)
                    }
                    composable("Chapters") {
                        val viewModel = hiltViewModel<MainViewModel>()
                        ChapterConnectivityStatus(navController,viewModel)
                    }
                    composable(
                        "Verse/{chapterNumber}/{chapterTitle}/{chapterSummary}/{verseCount}",
                        arguments = listOf(navArgument("chapterNumber") { type = NavType.IntType },
                            navArgument("chapterTitle") { type = NavType.StringType },
                            navArgument("chapterSummary") { type = NavType.StringType })

                    ) { backStackEntry ->

                        val viewModel = hiltViewModel<MainViewModel>()
                            val chapterNumber = backStackEntry.arguments?.getInt("chapterNumber") ?: -1
                            val chapterTitle = backStackEntry.arguments?.getString("chapterTitle") ?: ""
                            val chapterSummary = backStackEntry.arguments?.getString("chapterSummary") ?: ""
                            val verseCount = backStackEntry.arguments?.getString("verseCount") ?: ""

                        if (chapterNumber != -1) {
                            Verse(navController, chapterNumber, chapterTitle, chapterSummary, viewModel, verseCount)
                        } else {
                            // Handle the error case where the chapter number is invalid
                            Log.e("verse", "Invalid chapter number: $chapterNumber")
                        }
                    }
                    composable(
                        "VerseDetail/{chapterNumber}/{verseNumber}",
                        arguments = listOf(
                            navArgument("chapterNumber") { type = NavType.IntType },
                            navArgument("verseNumber") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->

                        val viewModel = hiltViewModel<MainViewModel>()
                        val chapterNumber = backStackEntry.arguments?.getInt("chapterNumber") ?: -1
                        val verseNumber = backStackEntry.arguments?.getInt("verseNumber") ?: -1

                        if (chapterNumber != -1 && verseNumber != -1) {
                            VerseDetail(navController, chapterNumber, verseNumber, viewModel)
                        } else {
                            // Handle the error case where the chapter number is invalid
                            Log.e("verse", "Invalid verse number: $chapterNumber : $verseNumber")
                        }
                    }
//                    composable("Saved") {
//                        SaveChapterAndVerse(navController)
//                    }
                }
            }
        }
    }
}

