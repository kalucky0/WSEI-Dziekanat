package dev.kalucky0.wsei

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import dev.kalucky0.wsei.data.AppDatabase
import dev.kalucky0.wsei.data.Authentication
import dev.kalucky0.wsei.data.SynchronizeData
import dev.kalucky0.wsei.data.models.Credentials
import dev.kalucky0.wsei.databinding.ActivityLoginBinding
import java.io.IOException
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    private lateinit var auth: Authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = Authentication(this)

        if (Utils.db == null)
            Utils.db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "wsei-db"
            ).fallbackToDestructiveMigration().build()

        var formFields: List<String> = ArrayList()

        Thread {
            try {
                val data = auth.getData("https://dziekanat.wsei.edu.pl/Konto/LogowanieStudenta")

                runOnUiThread {
                    if (data!!.contains("wpisz kod z obrazka")) auth.solveCaptcha()
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
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
        Thread {
            val test = auth.tryLogin(login, password, formFields)
            if (test!!.contains("/Konto/Zdjecie/")) {
                Utils.db!!.credentialsDao().insertAll(
                    Credentials(
                        0,
                        login,
                        password
                    )
                )
                SynchronizeData {
                    with(sharedPref.edit()) {
                        putString("sessionId", Utils.sessionId)
                        apply()
                    }

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            } else {
                Snackbar.make(
                    binding!!.root,
                    getString(R.string.login_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }.start()
    }

}