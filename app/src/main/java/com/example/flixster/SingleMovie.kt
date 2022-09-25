package com.example.flixster

import com.google.gson.annotations.SerializedName

class SingleMovie {
    @SerializedName("name")
    var tvName: String? = null

    @SerializedName("overview")
    var tvdescription: String? = null

    @SerializedName("poster_path")
    var tvImageUrl: String? = null

    @SerializedName("id")
    var tvId: Int? = null

    @SerializedName("vote_average")
    var tvRating: Float? = null
}