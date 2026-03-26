package com.easygym.domain.model


data class Group(
    val id: String,
    val name: String,
    val description: String?,
    val coachFullName: String?,
    val coachId: String?,
    val ageMin: Int?,
    val ageMax: Int?,
    val athleteCount: Int?,
    val active: Boolean,
)