package com.example.translate.model.data_sourse.api

import com.example.translate.model.data.dto.DataModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiSkyeng {

    @GET (END_POINT_API_SKYEND_SEARCH)
    fun search(@Query (QUERY_API_SKYEND_SEARCH_SEARCH) text: String): Single<DataModel>

    companion object{
        private const val END_POINT_API_SKYEND_SEARCH = "words/search"
        private const val QUERY_API_SKYEND_SEARCH_SEARCH  = "search"
    }

}