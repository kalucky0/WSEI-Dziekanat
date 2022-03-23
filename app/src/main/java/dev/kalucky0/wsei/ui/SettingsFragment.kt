package dev.kalucky0.wsei.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import dev.kalucky0.wsei.LoginActivity
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.Authentication
import dev.kalucky0.wsei.data.SynchronizeData
import dev.kalucky0.wsei.data.models.Credentials
import java.io.IOException


class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var auth: Authentication

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        auth = Authentication(requireContext())

        val syncButton: Preference? = findPreference("sync_now")
        val snakeButton: Preference? = findPreference("snake_game")
        val pacmanButton: Preference? = findPreference("pacman_game")
        val spaceInvadersButton: Preference? = findPreference("space_invaders_game")
        val githubButton: Preference? = findPreference("github")
        val reportButton: Preference? = findPreference("report_bug")
        val logoutButton: Preference? = findPreference("logout")

        val sharedPref = activity?.getSharedPreferences("wsei-app", Context.MODE_PRIVATE)

        syncButton?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
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

        snakeButton?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.snakeFragment)
            true
        }

        pacmanButton?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Toast.makeText(requireContext(), "Waka! Waka! Waka!", Toast.LENGTH_SHORT).show()
            true
        }

        spaceInvadersButton?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Toast.makeText(requireContext(), "Space Invaders game coming soon", Toast.LENGTH_SHORT)
                .show()
            true
        }

        githubButton?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/kalucky0/WSEI-Dziekanat"))
            startActivity(browserIntent)
            true
        }

        reportButton?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/kalucky0/WSEI-Dziekanat/issues/new")
            )
            startActivity(browserIntent)
            true
        }

        logoutButton?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Thread {
                Utils.db?.clearAllTables()
            }.start()

            Toast.makeText(requireContext(), getString(R.string.logging_out), Toast.LENGTH_LONG)
                .show()

            sharedPref?.edit()?.clear()?.apply()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

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