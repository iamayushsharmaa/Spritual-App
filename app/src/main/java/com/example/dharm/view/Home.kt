@file:Suppress("UNUSED_EXPRESSION")

package com.example.dharm.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
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
import com.example.dharm.viewmodel.MainViewModel
import kotlinx.coroutines.delay



@Preview
@Composable
fun Preview(){
    Home(rememberNavController())
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController:NavController, ){

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hinduism",
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
   ){
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding())
            ){

          ImageVerse(navController)
          Books(navController)

        }
    }
}


@Composable
fun ImageVerse(navController: NavController){
    Card (modifier = Modifier
        .width(400.dp)
        .height(200.dp)
        .padding(13.dp)
        ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(id = R.drawable.bhagwatgeetakrishnaimage),
                contentDescription ="firstImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = 0.8f // Adjust the alpha value to set transparency (0.0f to 1.0f)
                    }
            )
            Column (modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp, 12.dp, 0.dp, 0.dp)){
                Text(
                    text = "Verse of the day",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = 1.0f // Adjust the alpha value to set transparency (0.0f to 1.0f)
                        }
                )
                Text(
                    text = "Jai shree ram ji ki",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .graphicsLayer {
                            alpha =
                                1.0f // Adjust the alpha value to set transparency (0.0f to 1.0f)
                        }
                )
            }
        }

    }
}




@Composable
fun Books(navController: NavController) {

    var isLoading by remember { mutableStateOf(true) }

    // Launch a coroutine to simulate loading delay
    LaunchedEffect(key1 = true) {
        delay(2000)
        isLoading = false
    }

    val bookNames = listOf(
        "Bhagwat Geeta",
        "Mahabharat",
        "Ramayan"
    )
    Column {
        Text(
            text = "Books",
            fontSize = 29.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 20.dp, top = 30.dp)
        )


        // LazyColumn for displaying book list
        LazyColumn(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            items(bookNames) { bookName ->
                ShimmerListItem(
                    isLoading = isLoading,
                    contentAfterLoading = {
                        BookList(bookName, navController = navController)

                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                )
            }
        }
    }
}

