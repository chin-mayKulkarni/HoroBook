package com.chinmay.horobook.model

import io.reactivex.Single
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SongsApi {

   /* @GET("DevTides/DogsApi/master/dogs.json")
    fun getSongs() : Single<List<SongData>>*/

    @GET("Authentication/DeviceLogin/{auth_key}")
    fun authenticate(@Path("auth_key") auth_key: String) : Single<Authentication>

    @GET("Songs/SongAlbums/{auth_key}")
    fun getSongsAlbumList(@Path("auth_key") auth_key: String) : Single<List<SongData>>

    @GET("Songs/DevotionalSongs/{albumId}/{auth_key}")
    fun getSongsList(@Path("albumId") albumId : String, @Path("auth_key") auth_key : String) : Single<List<SongsListData>>

    @GET("Songs/LyricAlbums/{auth_key}")
    fun getLyricsAlbumList(@Path("auth_key") auth_key: String) : Single<List<SongData>>

    @GET("Songs/SongLyrics/{albumId}/{auth_key}")
    fun getLyricsList(@Path("albumId") albumId : String, @Path("auth_key") auth_key : String) : Single<List<LyricsListData>>


    @POST("Feedback/AddFeedback")
    fun submitFeedback(@Body body: JSONObject): Single<List<SongData>>

}