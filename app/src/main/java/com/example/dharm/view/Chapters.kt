package com.example.dharm.view


import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dharm.R
import com.example.dharm.view.ads.BannerAds
import com.example.dharm.viewmodel.MainViewModel
import com.example.foradsonly.ads.loadInterstitialAd
import com.example.foradsonly.ads.showInterstitialAds


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chapters(navController: NavController, viewModel: MainViewModel){

    val chapters by viewModel.chapters.collectAsState(initial = emptyList())
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(key1 = true) {
        isLoading = false
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Srimad Bhagvad Gita",
                        fontSize = 29.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.ExtraBold,
                    )
                },
                actions ={
                    IconButton(onClick = { navController.navigate("Saved")}) {
                        Icon(painter = painterResource(id = R.drawable.savefilled_icon), contentDescription = "Saved Verses")
                    }
                }
            )
        }
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding()),
        ){
            ImageVerse(navController)

            BannerAds(modifier = Modifier.padding(vertical = 5.dp))

            Text(text = "Chapters",
                fontSize = 29.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 20.dp, top = 25.dp)
            )
            LazyColumn(modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
            ) {
                items(chapters){ chapter->
                    ShimmerListItem(
                        isLoading = isLoading,
                        contentAfterLoading = {
                            ChapterList(
                                chapter = chapter.name_translated,
                                number = chapter.chapter_number,
                                navController = navController,
                                chapterSummary = chapter.chapter_summary,
                                verseCount = chapter.verses_count,
                                viewModel = viewModel
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChapterList(
    chapter: String,
    number: Int,
    navController: NavController,
    chapterSummary: String,
    verseCount: Int,
    viewModel: MainViewModel
){
    val context = LocalContext.current
    val activity = context as? Activity
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(70.dp)
            .clickable {
                onChapterClick(
                    number,
                    chapter,
                    navController,
                    chapterSummary,
                    verseCount,
                    viewModel,
                    context
                )
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Chapter $number : $chapter",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 12.dp, top = 15.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.next_btn),
                contentDescription = "Next to Chapter",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp, 19.dp, 8.dp, 8.dp),
            )
        }
    }
}


fun onChapterClick(
    currentChapterNumber: Int,
    currentChapter: String,
    navController: NavController,
    chapterSummary: String,
    verseCount: Int,
    viewModel: MainViewModel,
    context: Context
) {
    loadInterstitialAd(context)
    showInterstitialAds(context,navController,currentChapterNumber,currentChapter,chapterSummary,verseCount)
}


@Preview
@Composable
fun Okay() {
    Chapters(navController = rememberNavController(), viewModel = viewModel())
}