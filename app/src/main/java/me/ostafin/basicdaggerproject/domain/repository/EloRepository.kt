package me.ostafin.basicdaggerproject.domain.repository

import me.ostafin.basicdaggerproject.data.network.ApiService
import javax.inject.Inject


class EloRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getHeHe(page: Int) = apiService.getHehe()

    fun heheCallback(callback: (Long) -> Unit) {
        callback.invoke(808)
    }

}