package dev.kalucky0.wsei

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class LauncherActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAnalytics.getInstance(this);

        val sharedPref = getSharedPreferences("wsei-app", Context.MODE_PRIVATE)
        Utils.sessionId = sharedPref.getString("sessionId", "").toString()

        if (Utils.sessionId.isEmpty())
            startActivity(Intent(this, LoginActivity::class.java))
        else
            startActivity(Intent(this, MainActivity::class.java))

        finish()
    }
}