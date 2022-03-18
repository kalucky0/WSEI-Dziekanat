package dev.kalucky0.wsei.ui.announcements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.R

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
        announcementsTable.adapter = AnnouncementsViewAdapter(emptyList())
    }
}