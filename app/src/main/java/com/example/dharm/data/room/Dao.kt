//package com.example.dharm.data.room
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//
//@Dao
//interface SavedChapterDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertChapters(savedChapters: SavedChapters)
//
//    @Query("SELECT * FROM SavedChapters")
//    fun getSavedChapters(): LiveData<List<SavedChapters>>
//
//    @Query("DELETE FROM SavedChapters WHERE id = :id")
//    fun deleteChapter(id: Int)
//
//    @Query("SELECT * FROM SavedChapters WHERE chapter_number = :chapterNumber")
//    fun getAParticularChapter(chapterNumber: Int): LiveData<SavedChapters>
//}
