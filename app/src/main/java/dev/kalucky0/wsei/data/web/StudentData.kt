package dev.kalucky0.wsei.data.web

import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Student
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

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
                getValue(personalData, 1),
                getValue(personalData, 2),
                getValue(personalData, 0),
                index.split("szary\">")[1].split("<")[0],
                index.split("<br>")[2].split("<br>")[0].trim(),
                getValue(personalData, 3),
                getText(generalData, 11),
                city,
                getValue(personalData, 5),
                getText(generalData, 17),
                maritalStatus,
                nationality,
                citizenship,
                getValue(personalData, 9) + " " + getValue(personalData, 10),
                getValue(personalData, 11),
                getValue(personalData, 12),
                getValue(personalData, 13),
                getValue(personalData, 14),
                getValue(personalData, 15),
                getValue(personalData, 16),
                getValue(personalData, 17),
                getValue(personalData, 18),
                getValue(personalData, 19),
                getText(addressData, 1),
                getText(addressData, 2),
                getText(addressData, 3),
                getText(addressData, 4),
                getText(addressData, 5),
                getText(addressData, 6),
                getText(addressData, 7),
                getText(addressData, 9),
                getText(addressData, 10),
                getText(addressData, 11),
                getText(addressData, 12),
                getText(addressData, 13),
                additional,
                getText(educationData, 1),
                getText(educationData, 3),
                Integer.parseInt(getText(educationData, 5, "0")),
                getText(educationData, 7) + ",\n" +
                        getText(educationData, 8) + ",\n" +
                        getText(educationData, 9),
                getText(educationData, 11),
                getText(educationData, 13),
                getText(educationData, 15),
                getText(educationData, 17),
                getText(educationData, 19),
                getText(educationData, 21),
                getText(educationData, 23),
                getText(educationData, 25),
                getText(educationData, 27)
            )
        }

        private fun getText(arr: Elements, i: Int, def: String = "-"): String {
            if (arr.size <= i) return def
            return arr[i].text()
        }

        private fun getValue(arr: Elements, i: Int): String {
            if (arr.size <= i) return "-"
            return arr[i].`val`()
        }

        private fun selectVal(doc: Document, id: String): String {
            return doc.select("#$id > option[selected]").text()
        }
    }
}
