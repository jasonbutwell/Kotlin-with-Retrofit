package com.smartherd.globofly.services

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface DestinationService {

    @GET("destination")
//    fun getDestinationList(@Query("country") country: String?,
//                           @Query("count") count: Int?) : Call<List<Destination>>

    fun getDestinationList(@QueryMap filter : HashMap<String,String>) : Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestinationById(@Path("id") id: Int) : Call<Destination>

}