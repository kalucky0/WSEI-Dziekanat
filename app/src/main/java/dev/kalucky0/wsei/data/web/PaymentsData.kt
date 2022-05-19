package dev.kalucky0.wsei.data.web

import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Payment
import kotlinx.datetime.LocalDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class PaymentsData {
    companion object {
        fun get(): ArrayList<Payment> {
            val payments: ArrayList<Payment> = ArrayList()
            val doc: Document =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/Finanse/StudentFinanse/Oplaty")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()

            val rows: Elements = doc.select(".dxgvDataRow_Aqua")
            for ((i, row) in rows.withIndex()) {
                val data = row.select("td").map { it.text().trim() }
                payments.add(
                    Payment(
                        i,
                        data[1],
                        LocalDate.parse(data[2]),
                        data[3],
                        data[4].split(" ")[0].toInt(),
                        data[5].split(" ")[0].toInt(),
                        LocalDate.parse(data[6].ifBlank { "1970-01-01" }),
                        data[7]
                    )
                )
            }
            return payments
        }
    }
}