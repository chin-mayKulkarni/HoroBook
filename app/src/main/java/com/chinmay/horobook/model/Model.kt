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

    @ColumnInfo(name="search_val")
    @SerializedName("query")
    val search_query: String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("artist")
    val lifeSpan: String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName("album_cover_image")
    val imageUrl: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

data class SongsListData(
    @SerializedName("id")
    val songId: String?,

    @SerializedName("song_image")
    val songImageUrl: String?,

    @SerializedName("song_name")
    val songName: String?,

    @SerializedName("artist")
    val songArtist: String?,

    @SerializedName("crawl_required")
    val doNotPlaySong: Boolean?,

    @SerializedName("media_file")
    val songUrl: String?,
)

data class LyricsListData(
    @SerializedName("id")
    val songId: String?,

    @SerializedName("song_lyric_image")
    val songImageUrl: String?,

    @SerializedName("song_name")
    val songName: String?,

    @SerializedName("query")
    val search_query: String?,

    @SerializedName("artist")
    val songArtist: String?,

    @SerializedName("lyrics")
    val songLyrics: String?,
)

data class Feedback(

    val Message: String?,

    val ERROR: String?,
)

data class Horoscope(
    val date_range: String?,

    val description: String?,

    val mood: String?,

    val color: String?,

    val lucky_number: String?,

    val lucky_time: String?,
)