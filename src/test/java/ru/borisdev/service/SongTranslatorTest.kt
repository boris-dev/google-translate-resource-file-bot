package ru.borisdev.service

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Test

internal class SongTranslatorTest {

    @Test
    fun translate() {
        val translator = SongTranslator(SongTranslatorTest::class.java.classLoader.getResource("sample.json").readText())
        println(translator.translate())
    }
}