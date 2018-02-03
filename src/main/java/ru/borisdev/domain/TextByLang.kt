package ru.liga.domain

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonValue


data class TextByLang(
        val langToLangInfo: MutableMap<String, LangInfo> = mutableMapOf()
)


class TextByLangConverter : Converter<TextByLang> {
    override fun fromJson(jv: JsonValue): TextByLang {
        val langMap = hashMapOf<String, LangInfo>()
        val langs = jv.obj ?: throw IllegalArgumentException("TextByLang have no languages")
        for (lang in langs) {
            val jsonLangParams: JsonObject = lang.value as JsonObject
            langMap[lang.key] = LangInfo(
                    jsonLangParams.string("name") ?: "",
                    jsonLangParams.string("description") ?: "",
                    jsonLangParams.string("midiText") ?: "")
        }
        return TextByLang(langMap)
    }

    override fun toJson(value: TextByLang): String? {
        val langMap = value.langToLangInfo
        val jv = JsonObject()
        for (lang in langMap) {
            val langInfo = lang.value
            jv.put(lang.key, hashMapOf(
                    "name" to langInfo.name,
                    "midiText" to langInfo.midiText,
                    "description" to langInfo.description
            ))
        }
        return jv.toJsonString()
    }

}