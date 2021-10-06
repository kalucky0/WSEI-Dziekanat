package dev.kalucky0.wsei.data.web

import android.util.Log
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Payment
import kotlinx.datetime.LocalDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class PaymentsData(callback: (ArrayList<Payment>) -> Unit) {
    init {
        Thread {
            val payments: ArrayList<Payment> = ArrayList()
            val doc: Document =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/Finanse/StudentFinanse/Oplaty")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()

            val rows: Elements = doc.select(".dxgvDataRow_Aqua")
            var i = 0
            for (row in rows) {
                val cols = row.select("td")
                val data = cols.map { it.text().trim() }
                Log.e(
                    "aa",
                    Payment(
                        0,
                        data[1],
                        LocalDate.parse(data[2]),
                        data[3],
                        data[4].split(" ")[0].toInt(),
                        data[5].split(" ")[0].toInt(),
                        LocalDate.parse(if (data[7].isBlank()) "1970-01-01" else data[7]),
                        data[8]
                    ).toString()
                )
            }
            callback(payments)
        }.start()
    }
}