package ru.borisdev.service

import com.beust.klaxon.Klaxon
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import ru.liga.domain.Ar
import ru.liga.domain.De
import ru.liga.domain.Es
import ru.liga.domain.Song


class SongTranslator(val sourceJson: String) {
    private val klaxon = Klaxon()
    private val translateService = TranslateOptions.getDefaultInstance().service
    fun translate(): String {

        val song = klaxon.parse<Song>(sourceJson) ?: throw IllegalArgumentException("cannot parse source json: $sourceJson")
        for (lesson in song.lessons) {
            for (exercise in lesson.exercises) {
                var textByLang = exercise.textByLang
                textByLang.de = De(trans(textByLang.en.name, "de"), textByLang.en.midiText, trans(textByLang.en.description, "de"))
                textByLang.ar = Ar(trans(textByLang.en.name, "ar"), textByLang.en.midiText, trans(textByLang.en.description, "ar"))
                textByLang.es = Es(trans(textByLang.en.name, "es"), textByLang.en.midiText, trans(textByLang.en.description, "es"))
            }
        }
        return klaxon.toJsonString(song)
    }

    private fun trans(sourceText: String, targetLanguage: String): String {
        return translateService.translate(
                sourceText,
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage(targetLanguage)).translatedText
    }
}