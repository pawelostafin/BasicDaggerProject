package me.ostafin.basicdaggerproject.domain.model

data class CharactersPageResponse(
    val info: Info,
    val results: List<Character>
)