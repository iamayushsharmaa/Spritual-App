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
import androidx.hilt.navigation.compose.hiltViewModel
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
    verseCount: String,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val verses by viewModel.allVersesUiState.collectAsState()
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(chapterNumber) {
        viewModel.fetchVerses(chapterNumber)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Srimad Bhagavad Gita",
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                item {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            text ="Chapter $chapterNumber",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp)
                        )

                        Spacer(modifier = Modifier.width(180.dp))

                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.unfilled_icon),
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = "Save Chapter"
                            )
                        }
                    }

                    Text(
                        text = chapterTitle,
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = chapterSummary,
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)

                        )

                        Text(
                            text = if (isExpanded) "Read less" else "Read more...",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .clickable { isExpanded = !isExpanded },
                        )
                    }
                    Text(text = "$verseCount Verses",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp)
                    )
                }

                when (verses) {
                    is UiState.Loading -> {
                            items(6){
                                ShimmerListItem(
                                    isLoading = true,
                                    contentAfterLoading = {},
                                )
                            }
                    }
                    is UiState.Success -> {
                        val allVerses = (verses as UiState.Success).data
                            items(allVerses){ verseItem->
                                VerseList(
                                    verseNumber = verseItem.verse_number,
                                    onVerseClick = {
                                        showRewardAds(context, navController, chapterNumber,verseItem.verse_number)
                                    }
                                )


                            }
                        }
                    is UiState.Error -> {
                        item {
                            Text(text = "Failed to fetch verses")
                        }
                    }
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
    verseNumber: Int,
    onVerseClick: () -> Unit
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
            .padding(vertical = 7.dp)
            .height(70.dp)
            .clickable {
                onVerseClick()
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
                fontSize = 18.sp,
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

