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

    private val _chapters = MutableStateFlow<List<ChaptersItem>>(emptyList())
    val chapters: StateFlow<List<ChaptersItem>> = _chapters.asStateFlow()

    private val _allVerses = MutableStateFlow<List<VerseItem>>(emptyList())
    val allVerses: StateFlow<List<VerseItem>> = _allVerses.asStateFlow()

    private val _verse = MutableStateFlow<VerseItem?>(null)
    val verse: StateFlow<VerseItem?> = _verse.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchChapters()
    }

    fun fetchChapters() {
        viewModelScope.launch {
            repository.getAllChapters().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _chapters.value = result.data
                        _uiState.value = UiState.Success
                    }
                    is Resource.Error -> {
                        _uiState.value =
                            UiState.Error(result.exception.message ?: "An error occurred")
                    }
                    Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                }
            }
        }
    }
    fun fetchVerses(chapterNumber: Int) {
        viewModelScope.launch {
            repository.getAllverses(chapterNumber).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _allVerses.value = result.data
                        _uiState.value = UiState.Success
                    }

                    is Resource.Error -> {
                        _uiState.value =
                            UiState.Error(result.exception.message ?: "An error occurred")
                    }
                    Resource.Loading -> {
                        _uiState.value = UiState.Loading
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
                        _verse.value = result.data
                        _uiState.value = UiState.Success
                    }
                    is Resource.Error -> {
                        _uiState.value =
                            UiState.Error(result.exception.message ?: "An error occurred")
                    }
                    Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                }
            }
        }
    }
}
