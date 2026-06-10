package kittoku.mvc.fragment

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import kittoku.mvc.R
import kittoku.mvc.preference.MvcPreference
import kittoku.mvc.preference.custom.HomeConnectorPreference
import kittoku.mvc.preference.accessor.getStringPrefValue
import kittoku.mvc.preference.accessor.getIntPrefValue
import kittoku.mvc.service.ACTION_VPN_CONNECT
import kittoku.mvc.service.ACTION_VPN_DISCONNECT
import kittoku.mvc.service.SoftEtherVpnService


internal class HomeFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.home, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachSwitchListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            // Check if configuration is valid before connecting
            if (!validateConfiguration()) {
                return
            }
            startVpnService(ACTION_VPN_CONNECT)
            Toast.makeText(context, getString(R.string.vpn_connecting), Toast.LENGTH_SHORT).show()
        } else {
            // User denied VPN permission, reset switch
            findPreference<HomeConnectorPreference>(MvcPreference.HOME_CONNECTOR.name)?.also {
                it.isChecked = false
            }
            Toast.makeText(context, getString(R.string.vpn_permission_denied), Toast.LENGTH_LONG).show()
        }
    }

    private fun validateConfiguration(): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val hostname = getStringPrefValue(MvcPreference.HOME_HOSTNAME, prefs)
        val port = getIntPrefValue(MvcPreference.SSL_PORT, prefs)
        val hubName = getStringPrefValue(MvcPreference.HOME_HUB, prefs)
        val username = getStringPrefValue(MvcPreference.HOME_USERNAME, prefs)
        val password = getStringPrefValue(MvcPreference.HOME_PASSWORD, prefs)

        if (hostname.isEmpty()) {
            Toast.makeText(context, "Error: Hostname is empty", Toast.LENGTH_LONG).show()
            return false
        }

        if (port == 0) {
            Toast.makeText(context, "Error: Port is not set", Toast.LENGTH_LONG).show()
            return false
        }

        if (hubName.isEmpty()) {
            Toast.makeText(context, "Error: Virtual HUB Name is empty", Toast.LENGTH_LONG).show()
            return false
        }

        if (username.isEmpty()) {
            Toast.makeText(context, "Error: Username is empty", Toast.LENGTH_LONG).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(context, "Error: Password is empty", Toast.LENGTH_LONG).show()
            return false
        }

        // Show configuration summary
        val configSummary = "Connecting to:\n" +
                "Host: $hostname:$port\n" +
                "HUB: $hubName\n" +
                "User: $username"
        Toast.makeText(context, configSummary, Toast.LENGTH_LONG).show()

        return true
    }

    private fun startVpnService(action: String) {
        val intent = Intent(requireContext(), SoftEtherVpnService::class.java).setAction(action)
        requireContext().startForegroundService(intent)
    }

    private fun attachSwitchListener() {
        findPreference<HomeConnectorPreference>(MvcPreference.HOME_CONNECTOR.name)!!.also {
            it.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newState ->
                if (newState == true) {
                    // Check if VPN permission is granted
                    val vpnIntent = VpnService.prepare(context)
                    if (vpnIntent != null) {
                        // Need to request VPN permission
                        startActivityForResult(vpnIntent, 0)
                    } else {
                        // VPN permission already granted, start connection
                        onActivityResult(0, Activity.RESULT_OK, null)
                    }
                } else {
                    // Disconnect VPN
                    startVpnService(ACTION_VPN_DISCONNECT)
                    Toast.makeText(context, getString(R.string.vpn_disconnecting), Toast.LENGTH_SHORT).show()
                }

                true
            }
        }
    }
}
