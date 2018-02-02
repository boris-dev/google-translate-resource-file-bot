package ru.liga.domain

data class LessonsItem(val exercises: List<ExercisesItem>,
                       val comingSoon: Boolean = false,
                       val name: String = "",
                       val guid: String = "",
                       val simplicitySettings: SimplicitySettings?)