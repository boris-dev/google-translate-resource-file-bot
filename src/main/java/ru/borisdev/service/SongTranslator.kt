package ru.borisdev.service

import com.beust.klaxon.Klaxon
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import ru.liga.domain.LangInfo
import ru.liga.domain.Song
import ru.liga.domain.TextByLangConverter
import java.util.*


class SongTranslator(val sourceJson: String) {
    private val klaxon = Klaxon()
    private val translateService = TranslateOptions.getDefaultInstance().service
    fun translate(): String {
        val song = klaxon.converter(TextByLangConverter()).parse<Song>(sourceJson) ?: throw IllegalArgumentException("cannot parse source json: $sourceJson")
        for (lesson in song.lessons) {
            for (exercise in lesson.exercises) {
                val textByLang = exercise.textByLang
                val en = textByLang.langToLangInfo["en"] ?: throw IllegalArgumentException("There is no EN for translate")

                for (langCode in arrayOf("de", "ar", "es", "fr", "pt", "pl", "it", "ko", "ja", "tr")) {
                    textByLang.langToLangInfo[langCode] = LangInfo(name = trans(en.name, langCode), description = trans(en.description, langCode))
                }

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