package com.example.dharm.data.api


import com.example.dharm.models.chapter.ChaptersItem
import com.example.dharm.models.verse.VerseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/v2/chapters/")
    suspend fun getAllChapters() : List<ChaptersItem>

    @GET("/v2/chapters/{chapterNumber}/verses/")
    suspend fun getAllVerse(
        @Path("chapterNumber") chapterNumber : Int
    ): List<VerseItem>

    @GET("/v2/chapters/{chapterNumber}/verses/{verseNumber}/")
    suspend fun getParticularVerse(
        @Path ("chapterNumber") chapterNumber: Int ,
        @Path ("verseNumber") verseNumber :Int
    ) : VerseItem

}