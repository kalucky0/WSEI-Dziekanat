package dev.kalucky0.wsei

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dev.kalucky0.wsei.data.SynchronizeData
import dev.kalucky0.wsei.data.models.Credentials
import dev.kalucky0.wsei.databinding.ActivityLoginBinding
import dev.kalucky0.wsei.databinding.DialogCaptchaBinding
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    private var captchaCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        var formFields: List<String> = ArrayList()

        Thread {
            try {
                val data = getData("https://dziekanat.wsei.edu.pl/Konto/LogowanieStudenta")

                runOnUiThread {
                    if (data!!.contains("wpisz kod z obrazka")) solveCaptcha()
                }
                val regex = Regex(
                    "<tr style=\"(.*?)\">.+?formularz_dane\">\\s+<input.+?name=\"([0-9A-f]+)\".+?type=\"([a-z]+)\"",
                    setOf(
                        RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL
                    )
                )
                val matches = data?.let { regex.findAll(it) }
                formFields = matches?.map { it.groupValues[2] }?.filter { it != "2442" }!!.toList()
                runOnUiThread { binding?.loginButton!!.isEnabled = true }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

        val sharedPref = getSharedPreferences("wsei-app", Context.MODE_PRIVATE)
        binding?.loginButton!!.setOnClickListener { authenticate(sharedPref, formFields) }
    }

    private fun authenticate(sharedPref: SharedPreferences, formFields: List<String>) {
        val login = URLEncoder.encode(binding?.loginField!!.editText?.text.toString(), "utf-8")
        val password =
            URLEncoder.encode(binding?.passwordField!!.editText?.text.toString(), "utf-8")
        Thread {
            val test =
                if (captchaCode.isEmpty()) tryLogin("${formFields[0]}=${login}&${formFields[1]}=${password}") else tryLogin(
                    "${formFields[0]}=${login}&${formFields[1]}=${password}&captcha=${captchaCode}"
                )
            if (test!!.contains("/Konto/Zdjecie/")) {
                Utils.db!!.credentialsDao().insertAll(
                    Credentials(
                        0,
                        login,
                        password
                    )
                )
                SynchronizeData(Utils.sessionId) {
                    with(sharedPref.edit()) {
                        putString("sessionId", Utils.sessionId)
                        apply()
                    }

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            } else {
                Log.e("", test)
                Snackbar.make(
                    binding!!.root,
                    getString(R.string.login_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }.start()
    }

    private fun solveCaptcha() {
        Utils.initHttpClient()
        val dialogBinding: DialogCaptchaBinding =
            DialogCaptchaBinding.inflate(LayoutInflater.from(this))
        AlertDialog.Builder(this)
            .setTitle("Wpisz kod z obrazka")
            .setView(dialogBinding.root)
            .setPositiveButton("Ok") { dialog, which ->
                captchaCode = dialogBinding.passwordField.editText?.text.toString()
            }.show()

        val picasso = Picasso.Builder(this@LoginActivity)
            .downloader(OkHttp3Downloader(Utils.downloaderClient))
            .build()
        picasso.load("https://dziekanat.wsei.edu.pl/Shared/Captcha?height=80&width=360")
            .into(dialogBinding.dialogImageview)
    }

    @Throws(IOException::class)
    fun tryLogin(credentials: String): String? {
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