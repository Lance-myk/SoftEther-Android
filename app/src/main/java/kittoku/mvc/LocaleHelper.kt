package kittoku.mvc

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.preference.PreferenceManager
import java.util.Locale

object LocaleHelper {
    private const val PREF_LANGUAGE = "app_language"
    private const val LANGUAGE_ENGLISH = "en"
    private const val LANGUAGE_CHINESE = "zh"

    fun setLocale(context: Context): Context {
        val language = getLanguage(context)
        return updateLocale(context, language)
    }

    fun setLanguage(context: Context, language: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString(PREF_LANGUAGE, language).apply()
        updateLocale(context, language)
    }

    fun getLanguage(context: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_LANGUAGE, LANGUAGE_ENGLISH) ?: LANGUAGE_ENGLISH
    }

    fun getLanguageDisplayName(language: String): String {
        return when (language) {
            LANGUAGE_CHINESE -> "中文"
            else -> "English"
        }
    }

    fun getAvailableLanguages(): List<Pair<String, String>> {
        return listOf(
            LANGUAGE_ENGLISH to "English",
            LANGUAGE_CHINESE to "中文"
        )
    }

    private fun updateLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }
}
