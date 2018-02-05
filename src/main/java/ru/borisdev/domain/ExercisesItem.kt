package ru.liga.domain

data class ExercisesItem(val textByLang: TextByLang,
                         val youtobeLink: String? = null,
                         val midiBase64: String = "",
                         val guid: String = "",
                         val type: String = "",
                         val tonika: String? = null)