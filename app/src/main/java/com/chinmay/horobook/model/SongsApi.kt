package com.chinmay.horobook.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SongsApi {

   /* @GET("DevTides/DogsApi/master/dogs.json")
    fun getSongs() : Single<List<SongData>>*/

    @GET("Songs/Albums/{auth_key}")
    fun getSongsAlbumList(@Path("auth_key") auth_key: String) : Single<List<SongData>>

    @GET("Authentication/DeviceLogin/{auth_key}")
    fun authenticate(@Path("auth_key") auth_key: String) : Single<Authentication>

    @GET("Songs/Songs/{albumId}/{auth_key}")
    fun getSongsList(@Path("albumId") albumId : String, @Path("auth_key") auth_key : String) : Single<List<SongsListData>>

}