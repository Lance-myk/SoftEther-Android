package kittoku.mvc.preference.custom

import kittoku.mvc.R
import android.content.Context
import android.util.AttributeSet
import androidx.preference.DropDownPreference
import kittoku.mvc.preference.MvcPreference
import javax.net.ssl.SSLContext

class SslVersionPreference(context: Context, attrs: AttributeSet) : DropDownPreference(context, attrs) {
    private val mvcPreference = MvcPreference.SSL_VERSION
    private val preferenceTitle = context.getString(R.string.setting_ssl_version)

    override fun onAttached() {
        super.onAttached()

        title = preferenceTitle
        summaryProvider = SimpleSummaryProvider.getInstance()

        SSLContext.getDefault().supportedSSLParameters.also {
            val added = arrayOf("DEFAULT") + it.protocols
            entries = added
            entryValues = added
        }
    }
}
