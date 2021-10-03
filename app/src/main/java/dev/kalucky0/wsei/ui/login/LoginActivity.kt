package dev.kalucky0.wsei.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dev.kalucky0.wsei.MainActivity
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.databinding.ActivityLoginBinding
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        Utils.sessionId = sharedPref.getString("sessionId", "").toString()
        if (Utils.sessionId != "") {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        var formFields: List<String> = List(0) { "" }

        Thread {
            try {
                val data = getData("https://dziekanat.wsei.edu.pl/Konto/LogowanieStudenta")

                runOnUiThread {
                    if (data!!.contains("wpisz kod z obrazka")) {
                        //TODO: Add dialog window for solving captcha
                        Toast.makeText(applicationContext, "Jest Captcha", Toast.LENGTH_LONG).show()
                    }
                }
                val regex = Regex(
                    "<tr style=\"(.*?)\">.+?formularz_dane\">\\s+<input.+?name=\"([0-9A-f]+)\".+?type=\"([a-z]+)\"",
                    setOf(
                        RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL
                    )
                )
                val matches = data?.let { regex.findAll(it) }
                formFields = matches?.map { it.groupValues[2] }?.filter { it != "2442" }!!.toList()
                runOnUiThread { binding.loginButton.isEnabled = true }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

        binding.loginButton.setOnClickListener {
            val login = URLEncoder.encode(binding.loginField.editText?.text.toString(), "utf-8")
            val password =
                URLEncoder.encode(binding.passwordField.editText?.text.toString(), "utf-8")
            Thread {
                val test = tryLogin("${formFields[0]}=${login}&${formFields[1]}=${password}")
                if (test!!.contains("/Konto/Zdjecie/")) {
                    with(sharedPref.edit()) {
                        putString("sessionId", Utils.sessionId)
                        apply()
                    }

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.login_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }.start()
        }
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