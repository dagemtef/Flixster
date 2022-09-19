package com.example.flixster

import com.google.gson.annotations.SerializedName

class SingleMovie {
    @SerializedName("original_title")
    var movieTitle: String? = null

    @SerializedName("poster_path")
    var movieImageUrl: String? = null

    @SerializedName("overview")
    var description: String? = null
}