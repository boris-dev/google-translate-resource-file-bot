package ru.borisdev.service

import com.beust.klaxon.Klaxon
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import ru.liga.domain.*


class SongTranslator(val sourceJson: String) {
    private val klaxon = Klaxon()
    private val translateService = TranslateOptions.getDefaultInstance().service
    fun translate(): String {
        val song = klaxon.converter(TextByLangConverter()).parse<Song>(sourceJson) ?: throw IllegalArgumentException("cannot parse source json: $sourceJson")
        for (lesson in song.lessons) {
            for (exercise in lesson.exercises) {
                var textByLang = exercise.textByLang
                val en = textByLang.langToLangInfo["en"] ?: throw IllegalArgumentException("There is no EN for translate")
                textByLang.langToLangInfo["de"] = LangInfo(trans(en.name, "de"), en.midiText, trans(en.description, "de"))
                textByLang.langToLangInfo["ar"] = LangInfo(trans(en.name, "ar"), en.midiText, trans(en.description, "ar"))
                textByLang.langToLangInfo["es"] = LangInfo(trans(en.name, "es"), en.midiText, trans(en.description, "es"))
            }
        }
        return klaxon.toJsonString(song)
    }

    private fun trans(sourceText: String?, targetLanguage: String?): String {
        return translateService.translate(
                sourceText,
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage(targetLanguage)).translatedText
    }
}