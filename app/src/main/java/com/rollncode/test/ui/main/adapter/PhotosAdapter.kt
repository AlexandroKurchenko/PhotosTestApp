package com.rollncode.test.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rollncode.test.R
import com.rollncode.test.repoository.db.model.PhotoItem
import com.squareup.picasso.Picasso

class PhotosAdapter(private val clickAction:(String)->Unit) : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    private val listDiffer: AsyncListDiffer<PhotoItem> =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<PhotoItem>() {
            override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean =
                (oldItem as? PhotoItem)?.id == (newItem as? PhotoItem)?.id

            override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean =
                (oldItem as? PhotoItem) == (newItem as? PhotoItem)
        })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view: LinearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false) as LinearLayout
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    fun submitData(data: List<PhotoItem>) {
        listDiffer.submitList(data)
    }

   inner class PhotoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(photoItem: PhotoItem) {
            val photo = view.findViewById<ImageView>(R.id.photo)
            val title = view.findViewById<TextView>(R.id.title)
            Picasso.get().load(photoItem.thumbnailUrl).into(photo)
            title.text = photoItem.title
            view.setOnClickListener {
                clickAction.invoke(photoItem.url)
            }
        }
    }
}