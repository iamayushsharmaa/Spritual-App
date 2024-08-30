package com.example.dharm.data.api


import com.example.dharm.models.chapter.ChaptersItem
import com.example.dharm.models.verse.VerseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {

    @GET("/v2/chapters/")
    fun getAllChapters() : Call<List<ChaptersItem>>


    @GET("/v2/chapters/{chapterNumber}/verses/")
    fun getAllVerse(@Path("chapterNumber") chapterNumber : Int): Call<List<VerseItem>>

    @GET("/v2/chapters/{chapterNumber}/verses/{verseNumber}/")
    fun getParticularVerse(@Path ("chapterNumber") chapterNumber: Int ,
                           @Path ("verseNumber") verseNumber :Int ) : Call<VerseItem>
}