package com.chinmay.horobook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SongData(
    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    val breedId: String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName("album_name")
    val dogBreed: String?,

    @ColumnInfo(name="life_span")
    @SerializedName("artist")
    val lifeSpan: String?,

    /*@ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    val breedGroup: String?,

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    val bredFor: String?,

    @SerializedName("temperament")
    val temperament: String?,*/

    @ColumnInfo(name = "dog_url")
    @SerializedName("album_cover_image")
    val imageUrl: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

data class SongsListData(
    @SerializedName("id")
    val songId: String?,

    @SerializedName("song_image")
    val songImageUrl : String?,

    @SerializedName("song_name")
    val songName : String?,

    @SerializedName("artist")
    val songArtist : String?,

    @SerializedName("firebase_url")
    val songUrl : String?,



)