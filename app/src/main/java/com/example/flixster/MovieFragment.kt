package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MovieFragment :  Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_list, container, false)
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(recyclerView)
        return view
    }

    private fun updateAdapter(recyclerView: RecyclerView) {
        val MAIN_URL =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1"
        val client = AsyncHttpClient()

        client.get(MAIN_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                t?.message?.let {
                    Log.e("MovieFragment", errorResponse)
                }
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.i("Main", "On Success $json")
                try {
                    val resultsJSON: JSONArray = json?.jsonObject?.get("results") as JSONArray
                    val moviesRawJSON: String = resultsJSON.toString()
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<SingleMovie>>() {}.type
                    val models: List<SingleMovie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                    recyclerView.adapter = MovieRecyclerViewAdapter(models)
                } catch (e: JSONException){
                    Log.e("Main", "Exception: $e")
                }

            }
        })
    }
}