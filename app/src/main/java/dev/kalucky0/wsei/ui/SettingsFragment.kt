package dev.kalucky0.wsei.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import dev.kalucky0.wsei.R
import androidx.preference.Preference
import com.google.android.material.snackbar.Snackbar
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.Authentication
import dev.kalucky0.wsei.data.SynchronizeData
import dev.kalucky0.wsei.data.models.Credentials
import java.io.IOException

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var auth: Authentication

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        auth = Authentication(requireContext())

        val button: Preference? = findPreference("sync_now")
        val sharedPref = activity?.getSharedPreferences("wsei-app", Context.MODE_PRIVATE)

        button?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
                getString(R.string.sync_started),
                Snackbar.LENGTH_SHORT
            ).show()

            Thread {
                val cred = Utils.db?.credentialsDao()!!.getAll()[0]
                synchronizeData(cred, sharedPref)
            }.start()
            true
        }
    }

    private fun synchronizeData(cred: Credentials, sharedPref: SharedPreferences?) {
        try {
            val data = auth.getData("https://dziekanat.wsei.edu.pl/Konto/LogowanieStudenta")

            activity?.runOnUiThread {
                if (data!!.contains("wpisz kod z obrazka")) auth.solveCaptcha()
            }
            val regex = Regex(
                "<tr style=\"(.*?)\">.+?formularz_dane\">\\s+<input.+?name=\"([0-9A-f]+)\".+?type=\"([a-z]+)\"",
                setOf(
                    RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL
                )
            )
            val matches = data?.let { regex.findAll(it) }
            val formFields = matches?.map { it.groupValues[2] }?.filter { it != "2442" }!!.toList()
            val test = auth.tryLogin(cred.login, cred.password, formFields)
            if (test!!.contains("/Konto/Zdjecie/")) {
                SynchronizeData {
                    with(sharedPref!!.edit()) {
                        putString("sessionId", Utils.sessionId)
                        apply()
                    }

                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.sync_success),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.sync_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}