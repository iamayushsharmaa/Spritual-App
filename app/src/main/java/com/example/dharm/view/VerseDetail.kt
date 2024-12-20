package com.example.dharm.view

import android.widget.ProgressBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dharm.R
import com.example.dharm.models.verse.Commentary
import com.example.dharm.models.verse.Translation
import com.example.dharm.ads.BannerAds
import com.example.dharm.models.chapter.UiState
import com.example.dharm.viewmodel.MainViewModel
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerseDetail(
    navController: NavController,
    chapterNumber: Int?,
    verseNumber: Int?,
    viewModel: MainViewModel
){
    LaunchedEffect(verseNumber) {
        viewModel.fetchParticularVerse(chapterNumber!!,verseNumber!!)
    }

    val verse by viewModel.verse.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val listOfTranslations = verse?.translations?.filter { it.language == "english" } ?: emptyList()
    val listOfTranslationSize = listOfTranslations.size

    val listOfCommentary = verse?.commentaries?.filter { it.language == "english" } ?: emptyList()


    Scaffold (
        topBar= {
            TopAppBar(
                title = {
                    Text(
                        text = "Srimad Bhagavad Gita",
                        fontSize = 29.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
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
                .verticalScroll(rememberScrollState())
                .padding(top = it.calculateTopPadding())
        ) {

            when (uiState) {
                is UiState.Loading -> {
                   Box(
                       modifier = Modifier
                           .fillMaxSize()
                           .padding(30.dp),
                       contentAlignment = Alignment.Center
                   ) {
                       ProgressBar()
                   }
                }
                is UiState.Success -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, start = 15.dp, bottom = 10.dp, end = 15.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.unfilled_icon),
                            contentDescription = "saveIcon"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Verse $verseNumber",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    verse?.let { it1 -> ShlokCard(it1.text) }

                    BannerAds(modifier = Modifier)
                    Text(
                        text = "Translation",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(
                            top = 15.dp,
                            start = 10.dp,
                            bottom = 8.dp,
                            end = 10.dp
                        ),
                    )

                    TranslationCard(listOfTranslations)

                    BannerAds(modifier = Modifier)
                    Text(
                        text = "Commentary",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(
                            top = 15.dp,
                            start = 10.dp,
                            bottom = 8.dp,
                            end = 10.dp
                        ),
                    )
                    CommentaryCard(listOfCommentary)
                    BannerAds(modifier = Modifier)
                }
                is UiState.Error -> {
                    Text(text = (uiState as UiState.Error).message)
                }
            }
        }
    }
}
@Composable
fun ProgressBar(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier.width(50.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }}

@Composable
fun ShlokCard(text: String) {
    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 18.dp),
        modifier = Modifier
            .padding(top = 20.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ){

        Column (modifier = Modifier){
            Text(
                text = text,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 17.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center
            )
        }


    }
}

@Composable
fun TranslationCard(listOfTranslations: List<Translation>) {

    var isExpanded by remember { mutableStateOf(false) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }


    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            listOfTranslations.forEachIndexed { index, translation ->
                if (isExpanded || index == 0) {
                    Text(
                        text = "Author: ${translation.author_name}",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = translation.description,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 5,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth()
                    )
                }
            }
            if (!isExpanded) {
                Text(
                    text =
                    if (isExpanded)"Show less" else "Show more",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .clickable { isExpanded = true }
                )
            }

        }
    }
}

@Composable
fun CommentaryCard(listOfCommentary: List<Commentary>) {
    var isExpanded by remember { mutableStateOf(false) }

    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 15.dp),
        shape = RoundedCornerShape(10.dp)
    ){
        Column (modifier = Modifier
            .padding(10.dp)
        ){

            listOfCommentary.forEachIndexed { index, commentary ->
                Text(
                    text = "Author : ${commentary.author_name} ",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(9.dp)
                )
                Text(
                    text = commentary.description,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}