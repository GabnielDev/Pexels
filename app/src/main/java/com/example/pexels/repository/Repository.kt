package com.example.pexels.repository

import com.example.pexels.di.AppModule
import javax.inject.Inject

class Repository @Inject constructor(){

    suspend fun getImage(page: Int) = AppModule.provideClient().getImage(page)

    suspend fun getSearchImage(query: String, page: Int) = AppModule.provideClient().getSearchImage(query, page)

    suspend fun getVideo(page: Int) = AppModule.provideClient().getVideo(page)

    suspend fun getSearchVideo(query: String, page: Int) = AppModule.provideClient().getSearchVideo(query, page)

}