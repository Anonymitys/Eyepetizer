package com.ekko.repository

import com.ekko.api.core.ApiService
import com.ekko.repository.api.NavApi
import javax.inject.Inject

class NavRepository @Inject constructor(apiService: ApiService) {

    private val navApi = apiService.createApi<NavApi>()

    suspend fun nav(tabLabel: String) = navApi.nav(tabLabel)

}