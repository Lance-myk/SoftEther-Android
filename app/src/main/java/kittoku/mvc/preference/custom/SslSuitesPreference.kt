package kittoku.mvc.preference.custom

import kittoku.mvc.R
import android.content.Context
import android.util.AttributeSet
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import kittoku.mvc.preference.MvcPreference
import kittoku.mvc.preference.accessor.getSetPrefValue
import javax.net.ssl.SSLContext


internal class SslSuitesPreference(context: Context, attrs: AttributeSet) : MultiSelectListPreference(context, attrs) {
    private val mvcPreference = MvcPreference.SSL_SUITES
    private val preferenceTitle = context.getString(R.string.setting_ssl_suites)
    private val provider = SummaryProvider<Preference> {
        val currentValue = getSetPrefValue(mvcPreference, it.sharedPreferences!!)

        when (currentValue.size) {
            0 -> context.getString(R.string.setting_ssl_suites_empty)
            1 -> context.getString(R.string.setting_ssl_suites_single)
            else -> context.getString(R.string.setting_ssl_suites_count, currentValue.size)
        }
    }

    override fun onAttached() {
        super.onAttached()

        title = preferenceTitle
        summaryProvider = provider
        dependency = MvcPreference.SSL_DO_SELECT_SUITES.name

        SSLContext.getDefault().supportedSSLParameters.also {
            entries = it.cipherSuites
            entryValues = it.cipherSuites
        }
    }
}
