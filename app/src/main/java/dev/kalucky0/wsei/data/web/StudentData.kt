package dev.kalucky0.wsei.data.web

import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Student
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class StudentData {
    companion object {
        fun get(): Student {
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

            doc =
                Jsoup.connect("https://dziekanat.wsei.edu.pl/TokStudiow/StudentTwojeDane/Wyksztalcenie")
                    .header("Cookie", "ASP.NET_SessionId=${Utils.sessionId}").get()

            val educationData = doc.select("td.dane")

            return Student(
                0,
                personalData[1].`val`(),
                personalData[2].`val`(),
                personalData[0].`val`(),
                index.split("szary\">")[1].split("<")[0],
                index.split("<br>")[2].split("<br>")[0].trim(),
                personalData[3].`val`(),
                generalData[11].text(),
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
                educationData[1].text(),
                educationData[3].text(),
                Integer.parseInt(educationData[5].text()),
                educationData[7].text() + ",\n" + educationData[8].text() + ",\n" + educationData[9].text(),
                educationData[11].text(),
                educationData[13].text(),
                educationData[15].text(),
                educationData[17].text(),
                educationData[19].text(),
                educationData[21].text(),
                educationData[23].text(),
                educationData[25].text(),
                educationData[27].text()
            )
        }

        private fun selectVal(doc: Document, id: String): String {
            return doc.select("#$id > option[selected]").text()
        }
    }
}