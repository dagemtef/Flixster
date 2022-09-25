package com.example.flixster

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var showPoster: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var ratings: TextView
    private lateinit var seasons: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_screen)

        showPoster = findViewById<ImageView>(R.id.poster)
        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        ratings = findViewById(R.id.rating)
        seasons = findViewById(R.id.seasons)

        val showName = intent.getStringExtra("show_name")
        val showID = intent.getStringExtra("show_id")
        val showDesc = intent.getStringExtra("show_desc")
        val showRating = intent.getStringExtra("show_rating")
        val showImage = intent.getStringExtra("show_image")

        title.text = showName
        description.text = showDesc
        ratings.text = "Rating: $showRating/10"

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/$showImage")
            .into(showPoster)

        val client = AsyncHttpClient()
        client.get("https://api.themoviedb.org/3/tv/$showID?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US",object: JsonHttpResponseHandler(){
            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val lastEpisode : JSONObject = json.jsonObject.get("last_episode_to_air") as JSONObject
                val strSeason =  lastEpisode["season_number"]
                seasons.text = "Seasons: $strSeason"
            }
        })
    }


}