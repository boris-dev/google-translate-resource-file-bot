package ru.liga.domain

data class TextByLang(val ru: Ru?,
                      val en: En?,
                      var es: Es? = null,
                      var fr: Fr? = null,
                      var de: De? = null,
                      var pt: Pt? = null,
                      var pl: Pl? = null,
                      var ar: Ar? = null,
                      var ko: Ko? = null,
                      var ja: Ja? = null,
                      var tr: Tr? = null,
                      var it: It? = null

)