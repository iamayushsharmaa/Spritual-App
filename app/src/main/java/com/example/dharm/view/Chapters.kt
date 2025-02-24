package com.example.dharm.view


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dharm.R
import com.example.dharm.ads.BannerAds
import com.example.dharm.models.chapter.ChaptersItem
import com.example.dharm.models.chapter.UiState
import com.example.dharm.viewmodel.MainViewModel
import com.example.foradsonly.ads.loadInterstitialAd
import com.example.foradsonly.ads.showInterstitialAds


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chapters(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
){
    val chapters by viewModel.chaptersUiState.collectAsState()

    val context = LocalContext.current
    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ){
            ImageVerse(navController,viewModel)

            BannerAds(modifier = Modifier.padding(vertical = 5.dp))

            Text(text = "Chapters",
                fontSize = 29.sp,
                color = MaterialTheme.colorScheme.primary ,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 20.dp, top = 25.dp)
            )

            when (chapters) {
                is UiState.Loading -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {
                        items(6){
                            ShimmerListItem(
                                isLoading = true,
                                contentAfterLoading = {},
                            )
                        }
                    }
                }
                is UiState.Success -> {
                    val allChapters = (chapters as UiState.Success).data
                    LazyColumn (
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ){
                        items(allChapters){ chapter->
                            ChapterList(
                                chapter = chapter,
                                onChapterClick = {
                                    loadInterstitialAd(context)
                                    showInterstitialAds(
                                        context,
                                        navController,
                                        chapter.chapter_number,
                                        chapter.name_translated,
                                        chapter.chapter_summary,
                                        chapter.verses_count
                                    )
                                }
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = "Failed to fetch chapters",
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ChapterList(
    chapter: ChaptersItem,
    onChapterClick: () -> Unit = {}
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(70.dp)
            .clickable {
               onChapterClick()
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Chapter ${chapter.chapter_number} : ${chapter.name_translated}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 12.dp, top = 15.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = if (isSystemInDarkTheme()) {
                    R.drawable.next_btn_white // Replace with your dark mode icon resource
                } else {
                    R.drawable.next_btn
                }),
                contentDescription = "Next to Chapter",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp, 19.dp, 8.dp, 8.dp),
            )
        }
    }
}

