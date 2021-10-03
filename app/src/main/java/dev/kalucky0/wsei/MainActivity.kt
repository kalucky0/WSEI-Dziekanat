package dev.kalucky0.wsei

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial
import com.mikepenz.materialdrawer.iconics.iconicsIcon
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.*
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import dev.kalucky0.wsei.databinding.ActivityMainBinding
import dev.kalucky0.wsei.ui.schedule.ScheduleFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.root, binding.toolbar, com.mikepenz.materialdrawer.R.string.material_drawer_open, com.mikepenz.materialdrawer.R.string.material_drawer_close)
        binding.root.addDrawerListener(actionBarDrawerToggle)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ScheduleFragment.newInstance())
                .commitNow()
        }

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
            false
        }

        AccountHeaderView(this).apply {
            attachToSliderView(binding.slider)
            accountHeaderBackground.setImageResource(R.drawable.header_background)
            addProfiles(
                ProfileDrawerItem().apply {
                    nameText = "Jan Kowalski"
                    descriptionText = "email@example.com"
                    iconUrl = "https://dziekanat.wsei.edu.pl/Konto/Zdjecie/1"
                    identifier = 102
                }
            )
            onAccountHeaderListener = { view, profile, current ->
                false
            }
            withSavedInstance(savedInstanceState)
        }
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