package dev.kalucky0.wsei.data.web

import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Student
import kotlinx.datetime.LocalDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class StudentData(val callback: (Student) -> Unit) {
    init {
        Thread {
            var doc: Document =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/TokStudiow/StudentTwojeDane/Osobowe")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()

            val city = selectVal(doc, "Miasto")
            val maritalStatus = selectVal(doc, "StanCywilny")
            val nationality = selectVal(doc, "Narodowosc")
            val citizenship = selectVal(doc, "Obywatelstwo")

            val personalData = doc.select(".editable")
            val generalData = doc.select("td.dane")

            val index = doc.select("#td_naglowek table p")[0].html()

            doc =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/TokStudiow/StudentTwojeDane/Adresy")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()

            val addressData = doc.select("span.sensitive")
            val additional = doc.select("td.dane").last()!!.text()

            callback(
                Student(
                    0,
                    personalData[1].`val`(),
                    personalData[2].`val`(),
                    personalData[0].`val`(),
                    index.split("szary\">")[1].split("<")[0],
                    index.split("<br>")[2].split("<br>")[0].trim(),
                    personalData[3].`val`(),
                    LocalDate.parse(generalData[11].text()),
                    city,
                    personalData[5].`val`(),
                    generalData[17].text(),
                    maritalStatus,
                    nationality,
                    citizenship,
                    personalData[9].`val`() + " " + personalData[10].`val`(),
                    personalData[11].`val`(),
                    personalData[12].`val`(),
                    personalData[13].`val`(),
                    personalData[14].`val`(),
                    personalData[15].`val`(),
                    personalData[16].`val`(),
                    personalData[17].`val`(),
                    personalData[18].`val`(),
                    personalData[19].`val`(),
                    addressData[1].text(),
                    addressData[2].text(),
                    addressData[3].text(),
                    addressData[4].text(),
                    addressData[5].text(),
                    addressData[6].text(),
                    addressData[7].text(),
                    addressData[9].text(),
                    addressData[10].text(),
                    addressData[11].text(),
                    addressData[12].text(),
                    addressData[13].text(),
                    additional,
                    "",
                    LocalDate.parse("2000-01-01"),
                    0,
                    "",
                    "",
                    "",
                    LocalDate.parse("2000-01-01"),
                    LocalDate.parse("2000-01-01"),
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
        }.start()
    }

    fun selectVal(doc: Document, id: String): String {
        return doc.select("#$id > option[selected]").text()
    }
}