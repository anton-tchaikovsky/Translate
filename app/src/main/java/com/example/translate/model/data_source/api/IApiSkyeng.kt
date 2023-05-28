package com.example.translate.model.data_source.api

import com.example.translate.model.data.dto.DataModel
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