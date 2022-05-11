package dev.kalucky0.wsei.data.web

import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Announcement
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class AnnouncementsData {
    companion object {
        fun get(): ArrayList<Announcement> {
            val announcements: ArrayList<Announcement> = ArrayList()
            val doc: Document =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/TokStudiow/StudentOgloszenia/Ogloszenia")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()

            val rows: Elements = doc.select("table.dane tbody tr")

            for (row in rows) {
                val data = row.select("td").map { it.text().trim() }
                val uid = row.select("a").attr("href").split("/").last().toInt()

                announcements.add(
                    Announcement(
                        uid,
                        data[2],
                        data[1],
                        data[3],
                        row.attr("style").contains("normal")
                    )
                )
            }

            return announcements
        }
    }
}