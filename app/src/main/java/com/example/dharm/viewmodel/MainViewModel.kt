package com.example.dharm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dharm.data.api.Resource
import com.example.dharm.models.chapter.ChaptersItem
import com.example.dharm.models.chapter.UiState
import com.example.dharm.models.verse.VerseItem
import com.example.dharm.repository.ChapterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ChapterRepository
) : ViewModel() {

    private val _chaptersUiState = MutableStateFlow<UiState<List<ChaptersItem>>>(UiState.Loading)
    val chaptersUiState: StateFlow<UiState<List<ChaptersItem>>> = _chaptersUiState.asStateFlow()

    private val _allVersesUiState = MutableStateFlow<UiState<List<VerseItem>>>(UiState.Loading)
    val allVersesUiState: StateFlow<UiState<List<VerseItem>>> = _allVersesUiState.asStateFlow()

    private val _verseUiState = MutableStateFlow<UiState<VerseItem?>>(UiState.Success(null))
    val verseUiState: StateFlow<UiState<VerseItem?>> = _verseUiState.asStateFlow()

    private var lastFetchedChapter: Int? = null

    init {
        fetchChapters()
    }

    fun fetchChapters() {
        viewModelScope.launch {
            repository.getAllChapters().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _chaptersUiState.value = UiState.Success(result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _chaptersUiState.value = UiState.Error(
                            result.exception ?: Exception("Failed to fetch chapters")
                        )
                    }

                    Resource.Loading -> {
                        _chaptersUiState.value = UiState.Loading
                    }
                }
            }
        }
    }

    fun fetchVerses(chapterNumber: Int) {
        if (lastFetchedChapter == chapterNumber && _allVersesUiState.value is UiState.Success) {
            return
        }

        viewModelScope.launch {
            repository.getAllverses(chapterNumber).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val verses = result.data ?: emptyList()
                        _allVersesUiState.value = UiState.Success(verses)
                        lastFetchedChapter = chapterNumber
                    }

                    is Resource.Error -> {
                        _allVersesUiState.value = UiState.Error(
                            result.exception ?: Exception("Failed to fetch verses")
                        )
                    }

                    Resource.Loading -> {
                        _allVersesUiState.value = UiState.Loading
                    }
                }
            }
        }
    }

    fun fetchParticularVerse(chapterNumber: Int, verseNumber: Int) {
        viewModelScope.launch {
            repository.getParticularVerse(chapterNumber, verseNumber).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _verseUiState.value = UiState.Success(result.data)
                    }
                    is Resource.Error -> {
                        _verseUiState.value = UiState.Error(
                            result.exception ?: Exception("Failed to fetch verse")
                        )
                    }
                    Resource.Loading -> {
                        _verseUiState.value = UiState.Loading
                    }
                }
            }
        }
    }
}