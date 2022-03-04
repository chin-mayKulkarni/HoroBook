package com.chinmay.horobook.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SongsApiService {
    private  val BASE_URL_DOGS = "https://raw.githubusercontent.com/"


    private val api_dogs = Retrofit.Builder()
        .baseUrl(BASE_URL_DOGS)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SongsApi::class.java)

    private val BASE_URL = "https://chandusanjith.pythonanywhere.com/DaVinci/ApiV1/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SongsApi::class.java)

    fun authenticate(auth_key: String) : Single<Authentication>{
        return api.authenticate(auth_key)
    }

    fun getSongsAlbumList(auth_key: String) : Single<List<SongData>>{
        return api.getSongsAlbumList(auth_key)
    }

    fun getSongsList(auth_key: String, albumId : String) : Single<List<SongsListData>>{
        return api.getSongsList( albumId, auth_key)
    }

}