package me.ostafin.basicdaggerproject.data.network

import io.reactivex.rxjava3.core.Single
import me.ostafin.basicdaggerproject.domain.model.CharactersPageResponse
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    fun getHehe(): Single<CharactersPageResponse>

}
