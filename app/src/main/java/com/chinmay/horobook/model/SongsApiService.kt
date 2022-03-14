package com.chinmay.horobook.model

import com.chinmay.horobook.util.SharedPreferencesHelper
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SongsApiService {

    private val BASE_URL = "https://chandusanjith.pythonanywhere.com/DaVinci/ApiV1/"

    private var prefHelper = SharedPreferencesHelper()

    val headerInterceptor = Interceptor { chain ->
        var request = chain.request()

        request = request.newBuilder()
            .addHeader("auth_key", "prefHelper.getAuthKey().toString()")
            .build()
        val response = chain.proceed(request)

        response
    }

    private val okHttpBuilder: OkHttpClient.Builder =
        OkHttpClient.Builder().addInterceptor(headerInterceptor)


    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpBuilder.build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SongsApi::class.java)

    fun authenticate(auth_key: String): Single<Authentication> {
        return api.authenticate(auth_key)
    }

    fun getSongsAlbumList(auth_key: String): Single<List<SongData>> {
        return api.getSongsAlbumList(auth_key)
    }

    fun getSongsList(auth_key: String, albumId: String): Single<List<SongsListData>> {
        return api.getSongsList(albumId, auth_key)
    }

    fun getLyricsAlbumList(auth_key: String): Single<List<SongData>> {
        return api.getLyricsAlbumList(auth_key)
    }

    fun getLyricsList(auth_key: String, albumId: String): Single<List<LyricsListData>> {
        return api.getLyricsList(albumId, auth_key)
    }

    fun submitFeedback(jsonObject: JSONObject): Single<List<SongData>> {
        return api.submitFeedback(jsonObject)
    }


}