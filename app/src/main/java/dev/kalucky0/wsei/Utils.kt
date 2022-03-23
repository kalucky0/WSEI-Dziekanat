package dev.kalucky0.wsei

import android.content.Context
import dev.kalucky0.wsei.data.AppDatabase
import dev.kalucky0.wsei.data.models.Student
import okhttp3.OkHttpClient
import okhttp3.Request

class Utils {
    companion object {
        var db: AppDatabase? = null
        var sessionId: String = ""
        lateinit var downloaderClient: OkHttpClient
        lateinit var student: Student

        fun initHttpClient() {
            if(this::downloaderClient.isInitialized) return
            downloaderClient = OkHttpClient().newBuilder().addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
                    )
                    .header("Cookie", "ASP.NET_SessionId=${sessionId}")
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }.build()
        }

        fun toPixels(dp: Float, context: Context?): Int {
            val scale: Float = context!!.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}