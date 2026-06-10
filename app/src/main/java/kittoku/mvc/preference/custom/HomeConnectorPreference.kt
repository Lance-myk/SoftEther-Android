package kittoku.mvc.preference.custom

import kittoku.mvc.R
import android.content.Context
import android.content.SharedPreferences
import android.util.AttributeSet
import android.os.Handler
import android.os.Looper
import androidx.preference.SwitchPreferenceCompat
import kittoku.mvc.preference.MvcPreference
import kittoku.mvc.preference.accessor.getBooleanPrefValue


internal class HomeConnectorPreference(context: Context, attrs: AttributeSet) : SwitchPreferenceCompat(context, attrs) {
    private val mvcPreference = MvcPreference.HOME_CONNECTOR
    private var listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        if (key == mvcPreference.name) {
            val newValue = getBooleanPrefValue(mvcPreference, prefs)
            // Update UI on main thread
            Handler(Looper.getMainLooper()).post {
                isChecked = newValue
            }
        }
    }

    init {
        key = mvcPreference.name
        title = context.getString(R.string.home_connector)
    }

    override fun onAttached() {
        super.onAttached()

        isChecked = getBooleanPrefValue(mvcPreference, sharedPreferences!!)

        sharedPreferences!!.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onDetached() {
        super.onDetached()

        sharedPreferences!!.unregisterOnSharedPreferenceChangeListener(listener)
    }
}
