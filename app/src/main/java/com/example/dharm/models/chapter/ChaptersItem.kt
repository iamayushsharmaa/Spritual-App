package com.example.dharm.models.chapter

data class ChaptersItem(
    val chapter_number: Int,
    val chapter_summary: String,
    val id: Int,
    val name: String,
    val name_meaning: String,
    val name_translated: String,
    val name_transliterated: String,
    val verses_count: Int,
    val verse : List<String>
)