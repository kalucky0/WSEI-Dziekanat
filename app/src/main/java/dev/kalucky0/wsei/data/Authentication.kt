package dev.kalucky0.wsei.data

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.databinding.DialogCaptchaBinding
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

class Authentication(val context: Context) {
    private var captchaCode: String = ""

    fun solveCaptcha() {
        Utils.initHttpClient()
        val dialogBinding: DialogCaptchaBinding =
            DialogCaptchaBinding.inflate(LayoutInflater.from(context))
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.enter_code))
            .setView(dialogBinding.root)
            .setPositiveButton("Ok") { _, _ ->
                captchaCode = dialogBinding.passwordField.editText?.text.toString()
            }.show()

        val picasso = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(Utils.downloaderClient))
            .build()
        picasso.load("https://dziekanat.wsei.edu.pl/Shared/Captcha?height=80&width=360")
            .into(dialogBinding.dialogImageview)
    }

    @Throws(IOException::class)
    fun tryLogin(
        login: String,
        password: String,
        formFields: List<String>
    ): String? {
        val credentials = if (captchaCode.isEmpty())
            "${formFields[0]}=${login}&${formFields[1]}=${password}"
        else
            "${formFields[0]}=${login}&${formFields[1]}=${password}&captcha=${captchaCode}"

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, credentials)
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://dziekanat.wsei.edu.pl/Konto/LogowanieStudenta")
            .post(body)
            .addHeader(
                "user-agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
            )
            .addHeader("Cookie", "ASP.NET_SessionId=${Utils.sessionId}")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()
        client.newCall(request).execute().use { response ->
            return response.body()!!.string()
        }
    }

    @Throws(IOException::class)
    fun getData(url: String): String? {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .addHeader(
                "user-agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
            )
            .url(url)
            .build()
        client.newCall(request).execute().use { response ->
            val sessionIdRegex = Regex("ASP.NET_SessionId=(.*?);")
            Utils.sessionId =
                sessionIdRegex.find(response.headers("Set-Cookie").joinToString())!!.groupValues[1]
            return response.body()!!.string()
        }
    }
}