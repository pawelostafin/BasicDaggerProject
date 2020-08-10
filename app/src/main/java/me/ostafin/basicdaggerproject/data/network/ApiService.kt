package me.ostafin.basicdaggerproject.data.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    fun getHehe(): Single<CharactersPageResponse>

}

data class CharactersPageResponse(
    val info: Info,
    val results: List<Character>
)

data class Character(
    val id: Long,
    val name: String,
    val status: String
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

