package dev.kalucky0.wsei

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

@SuppressLint("CustomSplashScreen")
class LauncherActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("wsei-app", Context.MODE_PRIVATE)
        Utils.sessionId = sharedPref.getString("sessionId", "").toString()

        if (Utils.sessionId.isEmpty())
            startActivity(Intent(this, MainActivity::class.java))
        else
            startActivity(Intent(this, LoginActivity::class.java))
    }
}