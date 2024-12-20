package com.example.dharm.view


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dharm.R
import com.example.dharm.ads.showRewardAds
import com.example.dharm.models.chapter.UiState
import com.example.dharm.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Verse(
    navController: NavController,
    chapterNumber: Int,
    chapterTitle: String,
    chapterSummary: String,
    viewModel: MainViewModel,
    verseCount: String,
) {
    LaunchedEffect(chapterNumber) {
        viewModel.fetchVerses(chapterNumber)
    }

    val verses by viewModel.allVerses.collectAsState()
    var isExpanded by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Srimad Bhagavad Gita",
                        fontSize = 29.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(top = it.calculateTopPadding())
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background) // Adjust padding if needed
            ) {
                item {
                    Row {
                        Text(
                            text ="Chapter $chapterNumber",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 14.dp, top = 5.dp, bottom = 9.dp)
                        )

                        Spacer(modifier = Modifier.width(180.dp))

                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.unfilled_icon),
                                contentDescription = "Save Chapter"
                            )
                        }
                    }

                    Text(
                        text = chapterTitle,
                        fontSize = 29.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 14.dp, top = 5.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        // Main text content
                        Text(
                            text = chapterSummary,
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(start = 14.dp, top = 7.dp, end = 16.dp)
                                .fillMaxWidth(),
                        )

                        // Toggle button
                        Text(
                            text = if (isExpanded) "Read less" else "Read more...",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 14.dp, top = 5.dp)
                                .clickable { isExpanded = !isExpanded }, // Toggle the state
                        )
                    }
                    Text(text = "$verseCount Verses",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 14.dp, top = 25.dp)
                            .fillMaxWidth()
                            .clickable { }

                    )
                }

                when (uiState) {
                    is UiState.Loading -> {
                            items(6){
                                ShimmerListItem(
                                    isLoading = true,
                                    contentAfterLoading = {},
                                )
                            }
                    }
                    is UiState.Success -> {
                            items(verses){ verseItem->
                                VerseList(
                                    verseItem.verse_number,
                                    navController,
                                    chapterNumber,
                                    viewModel
                                )


                            }
                        }
                    is UiState.Error -> {
                        item {
                            Text(text = (uiState as UiState.Error).message)
                        }                    }
                }
            }
        }
    }
}

//fun saveChapter(viewModel: MainViewModel, chaptersItem: ChaptersItem, verses: List<VerseItem>) {
//
//    val savedChapters = SavedChapters(
//        chapter_number = chaptersItem.chapter_number,
//        verse = verses,
//        id = chaptersItem.id,
//        name = chaptersItem.name,
//        chapter_summary = chaptersItem.chapter_summary,
//        name_meaning = chaptersItem.name_meaning,
//        name_transliterated = chaptersItem.name_transliterated,
//        name_translated = chaptersItem.name_translated,
//        verses_count = chaptersItem.verses_count
//    )
//
//    viewModel.insertChapters(savedChapters)
//}

@Composable
fun VerseList(
    verseNumber : Int,
    navController: NavController,
    chapterNumber: Int,
    viewModel: MainViewModel
){

    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(70.dp)
            .clickable {
                onVerseClick(navController, chapterNumber, verseNumber, viewModel,context)
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Verse $verseNumber",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(12.dp,15.dp)
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

fun onVerseClick(
    navController: NavController,
    chapterNumber: Int,
    verseNumber: Int,
    viewModel: MainViewModel,
    context: Context
) {
   showRewardAds(context,navController,chapterNumber,verseNumber)
}

