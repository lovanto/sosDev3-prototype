package com.lovanto.sosdev.viewModel

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lovanto.sosdev.CustomOnItemClickListener
import com.lovanto.sosdev.R
import com.lovanto.sosdev.model.Favourite
import com.lovanto.sosdev.view.DetailActivity
import kotlinx.android.synthetic.main.item_row_users.view.*

import java.util.ArrayList

class FavouriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavouriteAdapter.NoteViewHolder>() {
    var listNotes = ArrayList<Favourite>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)

            notifyDataSetChanged()
        }

    fun addItem(fav: Favourite) {
        this.listNotes.add(fav)
        notifyItemInserted(this.listNotes.size - 1)
    }

    fun updateItem(position: Int, fav: Favourite) {
        this.listNotes[position] = fav
        notifyItemChanged(position, fav)
    }

    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_users, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = this.listNotes.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav: Favourite) {
            with(itemView){
                Glide.with(itemView.context)
                    .load(fav.avatar)
                    .apply(RequestOptions().override(250, 250))
                    .into(itemView.avatar)
                username.text = fav.username
                user_name.text = fav.name
                followers.text = fav.followers.toString()
                following.text = fav.following.toString()
                itemView.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_POSITION, position)
                        intent.putExtra(DetailActivity.EXTRA_NOTE, fav)
                        intent.putExtra(DetailActivity.EXTRA_DATA, fav)
                        activity.startActivity(intent)
                    }
                }))
            }
        }
    }
}