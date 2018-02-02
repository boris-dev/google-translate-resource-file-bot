package ru.borisdev.service

import com.beust.klaxon.Klaxon
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import ru.liga.domain.Ar
import ru.liga.domain.De
import ru.liga.domain.Song


class SongTranslator(val sourceJson: String) {
    private val klaxon = Klaxon()
    private val translateService = TranslateOptions.getDefaultInstance().service
    fun translate(): String {

        val song = klaxon.parse<Song>(sourceJson) ?: throw IllegalArgumentException("cannot parse source json: $sourceJson")
        for (lesson in song.lessons) {
            for (exercise in lesson.exercises) {
                var textByLang = exercise.textByLang
                textByLang.de = De(trans(textByLang.en.name, "de"), trans(textByLang.en.description, "de"))
                textByLang.ar = Ar("ARAB", "DESCR")

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