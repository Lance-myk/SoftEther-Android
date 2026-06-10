package kittoku.mvc.activity

import android.content.Context
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kittoku.mvc.BuildConfig
import kittoku.mvc.R
import kittoku.mvc.LocaleHelper
import kittoku.mvc.databinding.ActivityMainBinding
import kittoku.mvc.fragment.AboutFragment
import kittoku.mvc.fragment.HomeFragment
import kittoku.mvc.fragment.SettingFragment


class MainActivity : AppCompatActivity() {
    private lateinit var homeFragment: PreferenceFragmentCompat
    private lateinit var settingFragment: PreferenceFragmentCompat
    private lateinit var aboutFragment: PreferenceFragmentCompat

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "${getText(R.string.app_name_short)}: ${BuildConfig.VERSION_NAME}"
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.root.fitsSystemWindows = true
        setContentView(binding.root)

        homeFragment = HomeFragment()
        settingFragment = SettingFragment()
        aboutFragment = AboutFragment()

        object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> homeFragment
                    1 -> settingFragment
                    2 -> aboutFragment
                    else -> throw NotImplementedError()
                }
            }
        }.also {
            binding.pager.adapter = it
        }


        TabLayoutMediator(binding.tabBar, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_home)
                1 -> getString(R.string.tab_setting)
                2 -> getString(R.string.tab_about)
                else -> throw NotImplementedError()
            }
        }.attach()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }
}