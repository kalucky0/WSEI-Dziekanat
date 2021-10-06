package dev.kalucky0.wsei

import android.app.Application
import android.widget.Toast
import com.osama.firecrasher.CrashListener
import com.osama.firecrasher.FireCrasher
import android.content.Intent

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        FireCrasher.install(this,object : CrashListener() {
            override fun onCrash(throwable: Throwable) {
                Toast.makeText(this@Application, throwable.message, Toast.LENGTH_SHORT).show()

                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, throwable.stackTraceToString())
                sendIntent.type = "text/plain"

                val shareIntent = Intent.createChooser(sendIntent, null)
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(shareIntent)
            }
        })

    }
}