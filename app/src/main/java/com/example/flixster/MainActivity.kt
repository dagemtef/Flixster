package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixster.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException

private const val TAG = "MainActivity/"
private const val TV_URL = "https://api.themoviedb.org/3/tv/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1"


class MainActivity : AppCompatActivity() {
    private lateinit var tvRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val shows = mutableListOf<SingleMovie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        val showAdapter = TvRecyclerViewAdapter(this, shows)
        tvRecyclerView = findViewById(R.id.list)
        tvRecyclerView.adapter = showAdapter
        tvRecyclerView.layoutManager = GridLayoutManager(this, 2)

        val client = AsyncHttpClient()

        client.get(TV_URL, object : JsonHttpResponseHandler() {

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val resultsJSON: JSONArray = json?.jsonObject?.get("results") as JSONArray
                    val showsRawJSON: String = resultsJSON.toString()
                    val gson = Gson()
                    val arrayShowType = object : TypeToken<List<SingleMovie>>() {}.type
                    val models: List<SingleMovie> = gson.fromJson(showsRawJSON, arrayShowType)
                    shows.addAll(models)
                    showAdapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })

    }
}