package com.example.dharm.ads

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

private var rewardAd : RewardedAd? = null

fun loadRewardAd(context: Context){
    val adId = "ca-app-pub-3940256099942544/5224354917"
    val adRequest = AdRequest.Builder().build()
    RewardedAd.load(context,adId,adRequest,object : RewardedAdLoadCallback(){
        override fun onAdFailedToLoad(error: LoadAdError) {
            super.onAdFailedToLoad(error)
            rewardAd = null
        }
        override fun onAdLoaded(ad: RewardedAd) {
            super.onAdLoaded(ad)
            rewardAd = ad
            Log.d("adLoaded", "onAdLoaded: Ad loaded successfully ")
        }
    } )
}
fun RewardAdCallback(context: Context, chapterNumber: Int, verseNumber: Int, navController: NavController) {
    if (rewardAd == null) {
        Log.d("null ad", "Ad is null")
        return
    }

    rewardAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            super.onAdClicked()
            // Handle ad clicked event if needed
        }

        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
            // Navigate to VerseDetail screen when ad is dismissed
            navController.navigate("VerseDetail/$chapterNumber/$verseNumber")
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            // Navigate to VerseDetail screen if ad fails to show
            navController.navigate("VerseDetail/$chapterNumber/$verseNumber")
        }

        override fun onAdShowedFullScreenContent() {
            super.onAdShowedFullScreenContent()
            // Optionally handle when ad is shown
        }
    }
}

fun showRewardAds(context: Context, navController: NavController, chapterNumber: Int, verseNumber: Int) {
    val activity = context.findActivity()

    if (rewardAd != null && activity != null) {
        RewardAdCallback(
            context,
            chapterNumber,
            verseNumber,
            navController
        )
        rewardAd?.show(activity){}
    } else {
        Log.d("ad doesnot show", "The reward ad wasn't ready yet.")

        navController.navigate("VerseDetail/$chapterNumber/$verseNumber")
    }
}