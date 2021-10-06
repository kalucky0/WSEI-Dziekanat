package dev.kalucky0.wsei

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial
import com.mikepenz.materialdrawer.iconics.iconicsIcon
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.descriptionText
import com.mikepenz.materialdrawer.model.interfaces.iconUrl
import com.mikepenz.materialdrawer.model.interfaces.nameRes
import com.mikepenz.materialdrawer.model.interfaces.nameText
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import dev.kalucky0.wsei.data.AppDatabase
import dev.kalucky0.wsei.data.models.Student
import dev.kalucky0.wsei.databinding.ActivityMainBinding
import dev.kalucky0.wsei.ui.SettingsFragment
import dev.kalucky0.wsei.ui.schedule.ScheduleFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var student: Student
    private lateinit var accountHeader: AccountHeaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

//        UpdateChecker.checkForUpdate(binding.root, this)
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

        Utils.initHttpClient()
        setupDrawer(savedInstanceState)

        if (Utils.db == null)
            Utils.db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "wsei-db"
            ).build()

        Thread {
            student = Utils.db?.studentDao()!!.getAll()[0]
            runOnUiThread { updateHeader() }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun updateHeader() {
        binding.subtitle.text = "${student.name} ${student.surname}"
        accountHeader.addProfiles(ProfileDrawerItem().apply {
            nameText = student.name + " " + student.surname
            descriptionText = student.email
            iconUrl = "https://dziekanat.wsei.edu.pl/Konto/Zdjecie/1"
            identifier = 102
        })
    }

    private fun setupDrawer(savedInstanceState: Bundle?) {
        DrawerImageLoader.init(DrawerImgLoader(this))

        binding.slider.itemAdapter.add(
            PrimaryDrawerItem().apply {
                nameRes = R.string.schedule
                iconicsIcon = CommunityMaterial.Icon3.cmd_timetable
                isSelected = true
                identifier = 1
            },
            PrimaryDrawerItem().apply {
                nameRes = R.string.finances
                iconicsIcon = CommunityMaterial.Icon.cmd_currency_usd
                identifier = 2
            },
            PrimaryDrawerItem().apply {
                nameRes = R.string.announcements
                iconicsIcon = CommunityMaterial.Icon3.cmd_message_text_outline
                identifier = 3
            },
            DividerDrawerItem(),
            PrimaryDrawerItem().apply {
                nameRes = R.string.your_data
                iconicsIcon = CommunityMaterial.Icon.cmd_account_outline
                identifier = 4
            },
            PrimaryDrawerItem().apply {
                nameRes = R.string.settings
                iconicsIcon = CommunityMaterial.Icon.cmd_cog
                identifier = 5
            },
        )

        binding.slider.onDrawerItemClickListener = { v, drawerItem, position ->
            when (drawerItem.identifier) {
                1L -> replaceFragment(R.id.scheduleFragment, getString(R.string.schedule))
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

    private fun replaceFragment(fragment: Int, string: String) {
        binding.title.text = string
        navController.navigate(fragment)
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
}