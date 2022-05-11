package dev.kalucky0.wsei.ui.announcements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Announcement

class AnnouncementsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_announcements, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val announcementsTable: RecyclerView = view.findViewById(R.id.announcementsList)
        announcementsTable.layoutManager = LinearLayoutManager(context)
        announcementsTable.addItemDecoration(
            DividerItemDecoration(
                announcementsTable.context,
                DividerItemDecoration.VERTICAL
            )
        )

        Thread {
            var announcements: List<Announcement> = Utils.db?.announcementDao()!!.getAll()
            announcements = announcements.sortedBy { it.date }.reversed()

            activity?.runOnUiThread {
                announcementsTable.adapter = AnnouncementsViewAdapter(announcements)
            }
        }.start()
    }
}