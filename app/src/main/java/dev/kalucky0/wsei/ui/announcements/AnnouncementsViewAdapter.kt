package dev.kalucky0.wsei.ui.announcements

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.databinding.ItemAnnouncementsBinding

class AnnouncementsViewAdapter(
    private val values: List<String>
) : RecyclerView.Adapter<AnnouncementsViewAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemAnnouncementsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.content
        val dateView: TextView = binding.date
        val priorityView: TextView = binding.priority
        val item: LinearLayout = binding.announcementItem

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAnnouncementsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.contentView.text = ""
        holder.dateView.text = ""
        holder.priorityView.text = ""
    }

    override fun getItemCount(): Int = values.size
}