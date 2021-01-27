package md.merit.rickandmorty.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.sample.apollographqlandroid.ResultsQuery
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.lo_item.view.*
import md.merit.rickandmorty.R
import md.merit.rickandmorty.ui.DisplayActivity

class MainAdapter(private var items: List<ResultsQuery.Result>,  var context: Context) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.imgMain
        var characterName = itemView.tvName
        var episode = itemView.tvEpisode
        var location = itemView.tvLocation
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.lo_item, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = items[position]
        holder.characterName.text = character.name().toString()
        holder.episode.text = character.episode()?.get(0)?.name().toString()
        holder.location.text = character.origin()?.name().toString()

        Picasso.with(context)
            .load(
                StringBuilder(character.image().toString())
                    .toString()
            )
            .into(holder.image)

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, DisplayActivity::class.java))
        }
    }

    override fun getItemCount(): Int = items.size
}