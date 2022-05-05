package dev.kalucky0.wsei

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.*
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import dev.kalucky0.wsei.data.AppDatabase
import dev.kalucky0.wsei.data.models.Student
import dev.kalucky0.wsei.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var student: Student
    private lateinit var accountHeader: AccountHeaderView

    private var filterMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.root,
            binding.toolbar,
            com.mikepenz.materialdrawer.R.string.material_drawer_open,
            com.mikepenz.materialdrawer.R.string.material_drawer_close
        )
        binding.root.addDrawerListener(actionBarDrawerToggle)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val f = destination.id
            binding.toolbar.elevation =
                if (f == R.id.scheduleFragment || f == R.id.profileFragment) 0f else 8f
            filterMenu?.isVisible = f == R.id.scheduleFragment
        }

        Utils.initHttpClient()
        setupDrawer(savedInstanceState)

        if (Utils.db == null)
            Utils.db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "wsei-db"
            ).fallbackToDestructiveMigration().build()

        Thread {
            try {
                Utils.student = Utils.db?.studentDao()!!.getStudent()
                student = Utils.student
                runOnUiThread { updateHeader() }
            } catch (e: Exception) {
                logout()
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun updateHeader() {
        binding.subtitle.text = "${student.name} ${student.surname}"
        accountHeader.addProfiles(ProfileDrawerItem().apply {
            nameText = student.name + " " + student.surname
            descriptionText = student.personalEmail
            iconUrl = "https://dziekanat.wsei.edu.pl/Konto/Zdjecie/1"
            identifier = 102
        })
    }

    private fun setupDrawer(savedInstanceState: Bundle?) {
        DrawerImageLoader.init(DrawerImgLoader(this))

        binding.slider.itemAdapter.add(
            PrimaryDrawerItem().apply {
                nameRes = R.string.schedule
                iconRes = R.drawable.timetable
                isIconTinted = true
                isSelected = true
                identifier = 1
            },
            PrimaryDrawerItem().apply {
                nameRes = R.string.finances
                iconRes = R.drawable.currency_usd
                isIconTinted = true
                identifier = 2
            },
            PrimaryDrawerItem().apply {
                nameRes = R.string.announcements
                iconRes = R.drawable.message_text_outline
                isIconTinted = true
                identifier = 3
            },
            DividerDrawerItem(),
            PrimaryDrawerItem().apply {
                nameRes = R.string.your_data
                iconRes = R.drawable.account_outline
                isIconTinted = true
                identifier = 4
            },
            PrimaryDrawerItem().apply {
                nameRes = R.string.settings
                iconRes = R.drawable.cog
                isIconTinted = true
                identifier = 5
            },
        )

        binding.slider.onDrawerItemClickListener = { _, drawerItem, _ ->
            when (drawerItem.identifier) {
                1L -> replaceFragment(R.id.scheduleFragment, getString(R.string.schedule))
                2L -> replaceFragment(R.id.paymentsFragment, getString(R.string.finances))
                3L -> replaceFragment(R.id.announcementsFragment, getString(R.string.announcements))
                4L -> replaceFragment(R.id.profileFragment, getString(R.string.your_data))
                5L -> replaceFragment(R.id.settingsFragment, getString(R.string.settings))
            }
            false
        }

        accountHeader = AccountHeaderView(this).apply {
            attachToSliderView(binding.slider)
            accountHeaderBackground.setImageResource(R.drawable.header_background)
            onAccountHeaderListener = { _, _, _ -> false }
            withSavedInstance(savedInstanceState)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun replaceFragment(fragment: Int, string: String) {
        binding.title.text = string
        binding.subtitle.text = "${student.name} ${student.surname}"
        navController.navigate(fragment)
    }

    fun updateSubtitle(string: String) {
        binding.subtitle.text = string
    }

    fun updateTitle(string: String) {
        binding.title.text = string
    }

    private fun logout() {
        Thread {
            Utils.db?.clearAllTables()
        }.start()

        val sharedPref = getSharedPreferences("wsei-app", Context.MODE_PRIVATE)
        sharedPref?.edit()?.clear()?.apply()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onResume() {
        super.onResume()
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.schedule, menu)
        filterMenu = menu?.findItem(R.id.action_filter)
        return true
    }
}