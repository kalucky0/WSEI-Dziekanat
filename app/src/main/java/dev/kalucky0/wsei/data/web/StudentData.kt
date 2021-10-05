package dev.kalucky0.wsei.data.web

import android.util.Log
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Student
import kotlinx.datetime.LocalDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class StudentData(val callback: (Student) -> Unit) {
    init {
        Thread {
            var doc: Document =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/TokStudiow/StudentTwojeDane/Osobowe")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()
            val sensitiveData: Elements = doc.select(".sensitive.editable")
            val generalData: Elements = doc.select("td.dane")

            doc =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/TokStudiow/StudentTwojeDane/Adresy")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()
            val sensitiveAddress: Elements = doc.select("span.sensitive")

            callback(
                Student(
                    0,
                    sensitiveData[1].`val`(),
                    sensitiveData[0].`val`(),
                    sensitiveData[2].`val`(),
                    sensitiveData[3].`val`(),
                    LocalDate.parse(generalData[11].text()),
                    generalData[17].text(),
                    "${sensitiveData[5].`val`()} ${sensitiveData[6].`val`()}",
                    sensitiveAddress[10].text(),
                    sensitiveAddress[1].text(),
                    sensitiveAddress[2].text(),
                    sensitiveAddress[3].text(),
                    sensitiveAddress[4].text()
                )
            )
        }.start()
    }
}