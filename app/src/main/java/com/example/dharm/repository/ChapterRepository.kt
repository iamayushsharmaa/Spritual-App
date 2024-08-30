package com.example.dharm.repository


import android.annotation.SuppressLint
import android.util.Log
import com.example.dharm.data.api.ApiInterface
import com.example.dharm.models.chapter.ChaptersItem
import com.example.dharm.models.verse.VerseItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


interface ChapterRepository {
    fun getAllChapters(): Flow<List<ChaptersItem>>
    fun getAllverses(chapterNumber: Int): Flow<List<VerseItem>>
    fun getParticularVerse(chapterNumber: Int, verseNumber :Int) : Flow<VerseItem>
   // suspend fun insertChapters(savedChapters: SavedChapters)

}
class ChapterRepositoryImpl @Inject constructor(
    private val apiService: ApiInterface,
  //  private val savedChapterDao: SavedChapterDao
) : ChapterRepository {

    override fun getAllChapters(): Flow<List<ChaptersItem>> = callbackFlow {
        val callback = object : Callback<List<ChaptersItem>> {
            override fun onResponse(
                call: Call<List<ChaptersItem>>,
                response: Response<List<ChaptersItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    trySend(response.body()!!)
                } else {
                    trySend(emptyList())
                }
                close()
            }

            override fun onFailure(call: Call<List<ChaptersItem>>, t: Throwable) {
                close(t)
            }
        }

        val call = apiService.getAllChapters()
        call.enqueue(callback)

        awaitClose { call.cancel() }
    }

    override fun getAllverses(chapterNumber: Int): Flow<List<VerseItem>> = callbackFlow {
        val callback = object : Callback<List<VerseItem>> {
            override fun onResponse(
                call: Call<List<VerseItem>>,
                response: Response<List<VerseItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    trySend(response.body()!!)
                } else {
                    trySend(emptyList())
                }
                close()
            }

            override fun onFailure(call: Call<List<VerseItem>>, t: Throwable) {
                close(t)
            }
        }

        val call = apiService.getAllVerse(chapterNumber)
        call.enqueue(callback)

        awaitClose { call.cancel() }
    }


    @SuppressLint("SuspiciousIndentation")
    override fun getParticularVerse(chapterNumber: Int, verseNumber: Int): Flow<VerseItem> = callbackFlow{

        val callback = object : Callback<VerseItem> {
            override fun onResponse(call: Call<VerseItem>, response: Response<VerseItem>) {
                if (response.isSuccessful && response.body() != null) {
                    trySend(response.body()!!)
                }else{
                    Log.d("verse", "onResponse: else statement")
                }
            }

            override fun onFailure(call: Call<VerseItem>, t: Throwable) {
                close(t)
                Log.d("verse", "onFailure: partticular function ")
            }
        }
        val call = apiService.getParticularVerse(chapterNumber,verseNumber)
            call.enqueue(callback)

        awaitClose { call.cancel() }
    }

  // override suspend fun insertChapters(savedChapters: SavedChapters) = savedChapterDao.insertChapters(savedChapters)

}
