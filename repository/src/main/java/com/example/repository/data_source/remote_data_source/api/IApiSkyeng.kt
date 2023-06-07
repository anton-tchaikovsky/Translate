package com.example.repository.data_source.remote_data_source.api

import com.example.model.data.dto.DataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiSkyeng {

    @GET (END_POINT_API_SKYEND_SEARCH)
    suspend fun search (@Query (QUERY_API_SKYEND_SEARCH_SEARCH) text: String): DataModel

    companion object{
        private const val END_POINT_API_SKYEND_SEARCH = "words/search"
        private const val QUERY_API_SKYEND_SEARCH_SEARCH  = "search"
    }

}