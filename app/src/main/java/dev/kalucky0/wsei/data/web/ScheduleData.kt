package dev.kalucky0.wsei.data.web

import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Activity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ScheduleData(callback: (ArrayList<ArrayList<Activity>>) -> Unit) {
    init {
        Thread {
            var day = -1
            val data: ArrayList<ArrayList<Activity>> = ArrayList()
            val doc: Document =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/Plany/PlanyStudentow")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()

            val rows: Elements = doc.select(".dxgvDataRow_Aqua,.dxgvGroupRow_Aqua")
            for (row in rows) {
                if (row.text().contains("Data Zajęć")) {
                    day++
                    data.add(ArrayList())
                } else {
                    val cols = row.select("td")
                    val activity = Activity(
                        parseHour(cols[1].text()),
                        parseHour(cols[2].text()),
                        cols[5].text(),
                        cols[4].text(),
                        cols[6].text(),
                        cols[8].text()
                    )
                    data[day].add(activity)
                }
            }
            callback(data)
        }.start()
    }

    private fun parseHour(time: String): Float {
        val time = time.split(":");
        val hour: Int = time[0].toInt()
        val minutes: Float = time[1].toFloat() / 60
        return hour + minutes
    }
}