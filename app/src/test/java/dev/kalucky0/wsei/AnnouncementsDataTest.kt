package dev.kalucky0.wsei;

import dev.kalucky0.wsei.data.web.AnnouncementsData
import org.junit.Test

class AnnouncementsDataTest {
    @Test
    fun announcements_isCorrect() {
        Utils.sessionId = "your_session_id"
        assert(Utils.sessionId != "your_session_id") { "Session id is not set" }

        val announcements = AnnouncementsData.get()
        assert(announcements.size > 0) { "announcements is empty" }

        for (announcement in announcements) {
            assert(announcement.title.isNotEmpty()) { "announcement title is empty" }
            assert(announcement.priority.isNotEmpty()) { "announcement priority is empty" }
            assert(announcement.date.contains("-")) { "announcement date is empty" }
            println(announcement)
        }
    }
}