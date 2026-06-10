package kittoku.mvc.preference.custom

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference
import kittoku.mvc.LocaleHelper
import kittoku.mvc.R


internal class LanguagePreference(context: Context, attrs: AttributeSet) : ListPreference(context, attrs) {
    private val languages = LocaleHelper.getAvailableLanguages()

    init {
        title = context.getString(R.string.setting_language)
        summary = context.getString(R.string.setting_language_summary)
        key = "APP_LANGUAGE"

        entries = languages.map { it.second }.toTypedArray()
        entryValues = languages.map { it.first }.toTypedArray()

        val currentLanguage = LocaleHelper.getLanguage(context)
        setValue(currentLanguage)
    }

    override fun onAttached() {
        super.onAttached()

        val currentLanguage = LocaleHelper.getLanguage(context)
        setValue(currentLanguage)
    }
}
