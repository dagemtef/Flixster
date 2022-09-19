package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieRecyclerViewAdapter(
    private val movies: List<SingleMovie>): RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_movie, parent, false)
            return MovieViewHolder(view)
        }

        inner class MovieViewHolder(val movieView: View) : RecyclerView.ViewHolder(movieView) {
            var item: SingleMovie? = null
            val title: TextView = movieView.findViewById<View>(R.id.title) as TextView
            val description: TextView = movieView.findViewById<View>(R.id.description) as TextView
            val poster: ImageView = movieView.findViewById<View>(R.id.moviePoster) as ImageView

            override fun toString(): String {
                return title.toString()
            }
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movies[position]

            holder.item = movie
            holder.title.text = movie.movieTitle
            holder.description.text = movie.description

            Glide.with(holder.movieView)
                .load("https://image.tmdb.org/t/p/w500/" + movie.movieImageUrl)
                .centerInside()
                .into(holder.poster)
            }
        override fun getItemCount(): Int {
            return movies.size
        }
}