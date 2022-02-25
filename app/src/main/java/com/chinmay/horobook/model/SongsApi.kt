package com.chinmay.horobook.model

import io.reactivex.Single
import retrofit2.http.GET

interface SongsApi {

    @GET("DevTides/DogsApi/master/dogs.json")
    fun getSongs() : Single<List<SongData>>
}