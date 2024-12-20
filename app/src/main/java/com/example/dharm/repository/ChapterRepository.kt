package com.example.dharm.repository


import android.annotation.SuppressLint
import android.util.Log
import com.example.dharm.data.api.ApiInterface
import com.example.dharm.data.api.Resource
import com.example.dharm.models.chapter.ChaptersItem
import com.example.dharm.models.verse.VerseItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface ChapterRepository {
    suspend fun getAllChapters(): Flow<Resource<List<ChaptersItem>>>
    suspend fun getAllverses(chapterNumber: Int):  Flow<Resource<List<VerseItem>>>
    suspend fun getParticularVerse(chapterNumber: Int, verseNumber :Int) : Flow<Resource<VerseItem>>
    // suspend fun insertChapters(savedChapters: SavedChapters)
}


class ChapterRepositoryImpl @Inject constructor(
    private val apiService: ApiInterface,
    //  private val savedChapterDao: SavedChapterDao
) : ChapterRepository {

    override suspend fun getAllChapters(): Flow<Resource<List<ChaptersItem>>> = flow{
        try {
        emit(Resource.Loading)
        val chapters = apiService.getAllChapters()
            emit(Resource.Success(chapters))
    }catch(e : Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun getAllverses(
        chapterNumber: Int
    ): Flow<Resource<List<VerseItem>>> = flow{
        try {
            emit(Resource.Loading)
            val allVerses = apiService.getAllVerse(chapterNumber)
            emit(Resource.Success(allVerses))
        }catch(e : Exception) {
            emit(Resource.Error(e))
        }

    }

    override suspend fun getParticularVerse(
        chapterNumber: Int,
        verseNumber: Int
    ): Flow<Resource<VerseItem>> = flow{
        try {
            emit(Resource.Loading)
            val particularVerse = apiService.getParticularVerse(chapterNumber,verseNumber)
            emit(Resource.Success(particularVerse))
        }catch(e : Exception) {
            emit(Resource.Error(e))
        }

    }

    // override suspend fun insertChapters(savedChapters: SavedChapters) = savedChapterDao.insertChapters(savedChapters)

}
