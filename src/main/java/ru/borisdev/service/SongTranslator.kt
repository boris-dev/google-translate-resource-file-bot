package ru.borisdev.service

import com.beust.klaxon.Klaxon
import ru.liga.domain.Ar
import ru.liga.domain.De
import ru.liga.domain.Song
import java.io.File


class SongTranslator(val sourceJson: String) {
    private val klaxon = Klaxon()

    fun translate(): String {
        val song = klaxon.parse<Song>(sourceJson) ?: throw IllegalArgumentException("cannot parse source json: $sourceJson")
        for (lesson in song.lessons) {
            for (exercise in lesson.exercises) {
                var textByLang = exercise.textByLang
                textByLang.de = De("Der Exercise", "HH!")
                textByLang.ar = Ar("ARAB", "DESCR")
            }
        }
        return klaxon.toJsonString(song)
    }
}