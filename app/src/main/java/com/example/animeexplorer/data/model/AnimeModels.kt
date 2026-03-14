package com.example.animeexplorer.data.models

import com.google.gson.annotations.SerializedName

//Main API Response wrapper for anime list endpoints
data class AnimeResponse(
    @SerializedName("data")
    val data: List<Anime> = emptyList(),
    @SerializedName("pagination")
    val pagination: Pagination? = null
)

//Detailed anime response for single anime endpoint

data class AnimeDetailResponse(
    @SerializedName("data")
    val data: Anime
)


//Main Anime data model with comprehensive properties
data class Anime(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("images")
    val images: Images,
    @SerializedName("trailer")
    val trailer: Trailer? = null,
    @SerializedName("approved")
    val approved: Boolean,
    @SerializedName("titles")
    val titles: List<Title> = emptyList(),
    @SerializedName("title")
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String? = null,
    @SerializedName("title_japanese")
    val titleJapanese: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("source")
    val source: String? = null,
    @SerializedName("episodes")
    val episodes: Int? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("airing")
    val airing: Boolean = false,
    @SerializedName("aired")
    val aired: Aired? = null,
    @SerializedName("duration")
    val duration: String? = null,
    @SerializedName("rating")
    val rating: String? = null,
    @SerializedName("score")
    val score: Double? = null,
    @SerializedName("scored_by")
    val scoredBy: Int? = null,
    @SerializedName("rank")
    val rank: Int? = null,
    @SerializedName("popularity")
    val popularity: Int? = null,
    @SerializedName("genres")
    val genres: List<Genre> = emptyList(),
    @SerializedName("themes")
    val themes: List<Theme> = emptyList(),
    @SerializedName("demographics")
    val demographics: List<Demographic> = emptyList(),
    @SerializedName("synopsis")
    val synopsis: String? = null,
    @SerializedName("background")
    val background: String? = null,
    @SerializedName("season")
    val season: String? = null,
    @SerializedName("year")
    val year: Int? = null,
    @SerializedName("broadcast")
    val broadcast: Broadcast? = null,
    @SerializedName("producers")
    val producers: List<Producer> = emptyList(),
    @SerializedName("licensors")
    val licensors: List<Licensor> = emptyList(),
    @SerializedName("studios")
    val studios: List<Studio> = emptyList()
)

data class Images(
    @SerializedName("jpg")
    val jpg: ImageSet? = null,
    @SerializedName("webp")
    val webp: ImageSet? = null
)

data class ImageSet(
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("small_image_url")
    val smallImageUrl: String? = null,
    @SerializedName("large_image_url")
    val largeImageUrl: String? = null
)

data class Trailer(
    @SerializedName("youtube_id")
    val youtubeId: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("embed_url")
    val embedUrl: String? = null
)

data class Title(
    @SerializedName("type")
    val type: String,
    @SerializedName("title")
    val title: String
)

data class Aired(
    @SerializedName("from")
    val from: String? = null,
    @SerializedName("to")
    val to: String? = null,
    @SerializedName("prop")
    val prop: DateProp? = null,
    @SerializedName("string")
    val string: String? = null
)

data class DateProp(
    @SerializedName("from")
    val from: DateDetails? = null,
    @SerializedName("to")
    val to: DateDetails? = null
)

data class DateDetails(
    @SerializedName("day")
    val day: Int? = null,
    @SerializedName("month")
    val month: Int? = null,
    @SerializedName("year")
    val year: Int? = null
)

data class Genre(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Theme(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Demographic(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Broadcast(
    @SerializedName("day")
    val day: String? = null,
    @SerializedName("time")
    val time: String? = null,
    @SerializedName("timezone")
    val timezone: String? = null,
    @SerializedName("string")
    val string: String? = null
)

data class Producer(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Licensor(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Studio(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class Pagination(
    @SerializedName("last_page")
    val lastPage: Int = 1,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean = false,
    @SerializedName("current_page")
    val currentPage: Int = 1,
    @SerializedName("items")
    val items: PaginationItems? = null
)

data class PaginationItems(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("per_page")
    val perPage: Int = 25
)


//Character response for bonus feature
data class CharacterResponse(
    @SerializedName("data")
    val data: List<Character> = emptyList()
)

data class Character(
    @SerializedName("character")
    val character: CharacterInfo? = null,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("voice_actors")
    val voiceActors: List<VoiceActor> = emptyList()
)

data class CharacterInfo(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("images")
    val images: Images,
    @SerializedName("name")
    val name: String
)

data class VoiceActor(
    @SerializedName("person")
    val person: PersonInfo? = null,
    @SerializedName("language")
    val language: String? = null
)

data class PersonInfo(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)