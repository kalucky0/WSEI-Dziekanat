package dev.kalucky0.wsei.data.web

import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Schedule
import kotlinx.datetime.LocalDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ScheduleData(callback: (ArrayList<Schedule>) -> Unit) {
    init {
        Thread {
            val data: ArrayList<Schedule> = ArrayList()
            val doc: Document =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/Plany/PlanyStudentow")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()

            val rows: Elements = doc.select(".dxgvDataRow_Aqua,.dxgvGroupRow_Aqua")
            lateinit var date: LocalDate
            var i = 0
            for (row in rows) {
                if (row.text().contains("Data Zajęć")) {
                    date = LocalDate.parse(findDate(row.text()))
                } else {
                    val cols = row.select("td")
                    val activity = Schedule(
                        i,
                        date,
                        parseHour(cols[1].text()),
                        parseHour(cols[2].text()),
                        cols[5].text(),
                        cols[4].text(),
                        cols[6].text(),
                        cols[8].text()
                    )
                    i++
                    data.add(activity)
                }
            }
            callback(data)
        }.start()
    }

    private fun findDate(text: String): String {
        val regex = Regex("....-..-..")
        return regex.find(text)!!.groupValues[0]
    }

    private fun parseHour(time: String): Float {
        val time = time.split(":")
        val hour: Int = time[0].toInt()
        val minutes: Float = time[1].toFloat() / 60
        return hour + minutes
    }
}