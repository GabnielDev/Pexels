package com.example.pexels.network

import com.example.pexels.data.Image
import com.example.pexels.data.Video
import com.example.pexels.utils.Constants.APIKEY
import com.example.pexels.utils.Constants.IMAGE
import com.example.pexels.utils.Constants.SEARCHIMAGE
import com.example.pexels.utils.Constants.SEARCHVIDEO
import com.example.pexels.utils.Constants.VIDEO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers(APIKEY)
    @GET(IMAGE)
    suspend fun getImage(
        @Query("page") page: Int
    ): Response<Image>

    @Headers(APIKEY)
    @GET(VIDEO)
    suspend fun getVideo(
        @Query("page") page: Int
    ): Response<Video>

    @Headers(APIKEY)
    @GET(SEARCHIMAGE)
    suspend fun getSearchImage(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Response<Image>

    @Headers(APIKEY)
    @GET(SEARCHVIDEO)
    suspend fun getSearchVideo(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<Video>

}