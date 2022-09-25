package com.example.flixster

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val SHOW_EXTRA = "SHOW_EXTRA"
private const val TAG = "TvRecyclerViewAdapter"

class TvRecyclerViewAdapter(
    private val context: Context, private val shows: List<SingleMovie>): RecyclerView.Adapter<TvRecyclerViewAdapter.TvViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.single_movie, parent, false)
            return TvViewHolder(view)
        }

        override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
            val show = shows[position]

            holder.item = show
            holder.title.text = show.tvName

            Glide.with(holder.showView)
                .load("https://image.tmdb.org/t/p/w500/" + show.tvImageUrl)
                .centerInside()
                .into(holder.poster)
            }
        override fun getItemCount(): Int {
            return shows.size
        }

        inner class TvViewHolder(val showView: View) : RecyclerView.ViewHolder(showView),
            View.OnClickListener {

            var item: SingleMovie? = null
            val title: TextView = showView.findViewById<View>(R.id.title) as TextView
            val poster: ImageView = showView.findViewById<View>(R.id.moviePoster) as ImageView

            init {
                showView.setOnClickListener(this)
            }

            override fun onClick(showView: View?) {
                val show = shows[absoluteAdapterPosition]

                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("show_id", show.tvId.toString())
                intent.putExtra("show_name", show.tvName)
                intent.putExtra("show_desc", show.tvdescription)
                intent.putExtra("show_image", show.tvImageUrl)
                intent.putExtra("show_rating", show.tvRating.toString())
                context.startActivity(intent)
            }
        }
}


