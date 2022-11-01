package com.example.localization

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import java.util.*

object LocaleHelper {

    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private const val LANGUAGE_SHARE_PREF = "lang_pref"

    fun onAttach(context: Context): Context {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttach(context: Context, defaultLanguage: String): Context {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String {
        return getPersistedData(context, Locale.getDefault().language)
    }

    fun setLanguage(context: Context, langCode: String) {
        persist(context, langCode)
    }

    fun setLocale(context: Context, language: String): Context {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            updateResources(context, language)
        } else {
            updateResourcesLegacy(context, language)
        }
    }

    fun getLocaleContext(context: Context): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            updateResources(context, Locale.getDefault().language)
        } else {
            updateResourcesLegacy(context, Locale.getDefault().language)
        }
    }

    @Suppress("DEPRECATION")
    private fun getPersistedData(context: Context, defaultLanguage: String): String {
        val preferences = context.getSharedPreferences(LANGUAGE_SHARE_PREF, Context.MODE_PRIVATE)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
            ?: Locale.getDefault().language
    }

    @Suppress("DEPRECATION")
    private fun persist(context: Context, language: String?) {
        val preferences = context.getSharedPreferences(LANGUAGE_SHARE_PREF, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        return context.createConfigurationContext(configuration)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("ObsoleteSdkInt")
    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}