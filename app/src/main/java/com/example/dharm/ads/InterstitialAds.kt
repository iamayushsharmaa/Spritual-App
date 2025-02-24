package com.example.foradsonly.ads


import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.example.dharm.ads.findActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback



var interstitialAd: InterstitialAd? = null
var isAdLoading = false


fun loadInterstitialAd(context: Context) {
    isAdLoading = true
    InterstitialAd.load(
        context,
        "ca-app-pub-3940256099942544/1033173712", // Test Ad Unit ID
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
                isAdLoading = false
                Log.d("AdLoaded", "Interstitial ad loaded successfully")

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("AdLoadError", "Failed to load interstitial ad: ${adError.message}")
                interstitialAd = null
                isAdLoading = false
            }
        }
    )
}

fun addInterstitialCallbacks(
    context: Context,
    navController: NavController,
    currentChapterNumber: Int,
    chapterTitle: String,
    chapterSummary: String,
    verseCount: Int
) {
    interstitialAd?.let { ad ->
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d("AdDismissed", "Interstitial ad dismissed")
                // Navigate to the next screen after the ad is dismissed
                navController.navigate("Verse/$currentChapterNumber/$chapterTitle/$chapterSummary/$verseCount")
                interstitialAd = null
                Log.d("AdDismissed", "Interstitial ad dismissed")
                loadInterstitialAd(context) // Reload a new ad after dismissal
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Navigate to the next screen if the ad fails to show
                Log.d("AdShowError", "Failed to show interstitial ad: ${adError.message}")
                navController.navigate("Verse/$currentChapterNumber/$chapterTitle/$chapterSummary/$verseCount")
                interstitialAd = null
            }

            override fun onAdShowedFullScreenContent() {
                // Log when the ad is showing
                Log.d("AdShown", "Interstitial ad is showing")
            }
        }
    } ?: Log.d("AdShowError", "Interstitial ad is not ready yet")
}

fun showInterstitialAds(
    context: Context,
    navController: NavController,
    currentChapterNumber: Int,
    currentChapter: String,
    chapterSummary: String,
    verseCount: Int
) {
    val activity = context.findActivity()

    if (interstitialAd != null && activity != null) {
        addInterstitialCallbacks(
            context = context,
            navController = navController,
            currentChapterNumber = currentChapterNumber,
            chapterTitle = currentChapter,
            chapterSummary = chapterSummary,
            verseCount = verseCount
        )
        interstitialAd?.show(activity)
    } else {
        navController.navigate("Verse/$currentChapterNumber/$currentChapter/$chapterSummary/$verseCount")
    }
}




