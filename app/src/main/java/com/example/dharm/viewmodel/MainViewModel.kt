package com.example.dharm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dharm.models.chapter.ChaptersItem
import com.example.dharm.models.verse.Verse
import com.example.dharm.models.verse.VerseItem
import com.example.dharm.repository.ChapterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val chapterRepository: ChapterRepository
) : ViewModel() {

    val chapters: Flow<List<ChaptersItem>> = chapterRepository.getAllChapters()
    private val _chapterNumber = MutableStateFlow(1)
    val chapterNumber: StateFlow<Int> get() = _chapterNumber
    private val _verseNumber = MutableStateFlow<Int>(1)
    val verseNumber: StateFlow<Int> = _verseNumber

    @OptIn(ExperimentalCoroutinesApi::class)
    val verses: Flow<List<VerseItem>> = chapterNumber
        .flatMapLatest { chapterNumber ->
            Log.d("MainViewModel", "Fetching verses for chapter: $chapterNumber")
            chapterRepository.getAllverses(chapterNumber)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val particularVerse : Flow<VerseItem?> =
        combine(chapterNumber,verseNumber){ chapterNumber, verseNumber->
            chapterNumber to verseNumber
        }.flatMapLatest { (chapterNumber,verseNumber) ->
            chapterRepository.getParticularVerse(chapterNumber, verseNumber)

        }.stateIn(viewModelScope, SharingStarted.Lazily,null)



    init {
        fetchChapters()
    }

    private fun fetchChapters() {
        viewModelScope.launch {
            chapterRepository.getAllChapters().collect {
                Log.d("MainViewModel", "Fetched chapters: ${it.size}")
            }
        }
    }

    init {
        viewModelScope.launch {
            verses.collect { versesList ->
                Log.d("verse", "Collected verses for chapter ${_chapterNumber.value}: ${versesList.size}")
            }
        }
    }

    init {
        viewModelScope.launch {
            particularVerse.collect(){ verses->
                Log.d("verse", "colleting verse data : $_verseNumber ")
            }
        }
    }

    fun setChapterNumber(newChapterNumber: Int) {
        Log.d("verse", "Setting new chapter number: $newChapterNumber")
        _chapterNumber.value = newChapterNumber
    }

    fun setVerseNumber(newVerse: Int){
        Log.d("verse", "setVerseNumber: $newVerse ")
        _verseNumber.value = newVerse
    }

}
