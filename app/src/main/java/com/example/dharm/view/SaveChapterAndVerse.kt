@file:OptIn(ExperimentalFoundationApi::class)

package com.example.dharm.view

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dharm.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveChapterAndVerse(navController: NavController){

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    
    val tabs = listOf(
        TabItem(title = "Chapters"),
        TabItem(title = "Verse")
    )
    val pagerState = rememberPagerState {
        tabs.size
    }
    LaunchedEffect (selectedIndex){
        pagerState.animateScrollToPage(selectedIndex)
    }
    LaunchedEffect(pagerState.currentPage,pagerState.isScrollInProgress) {
        if(!pagerState.isScrollInProgress){
            selectedIndex = pagerState.currentPage
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Saved",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_button),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding())) {
            TabRow(selectedTabIndex = selectedIndex) {
                tabs.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        text = {
                            Text(text = item.title)
                        }
                    )
                }

            }
            HorizontalPager(state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->


                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {

                    Text(text = tabs[index].title)
                }
            }
        }
    }
}
@Preview
@Composable
fun Okayy (){
    SaveChapterAndVerse(navController = rememberNavController())
}

data class TabItem(
    val title : String
)

