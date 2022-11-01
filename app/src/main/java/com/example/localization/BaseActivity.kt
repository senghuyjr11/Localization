package com.example.localization

import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding : T

    @LayoutRes
    abstract fun getLayoutId(): Int

    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreference = getSharedPreferences("Lang", MODE_PRIVATE)
        if (getPrefer("LANGUAGE").isNullOrEmpty()) {
            LocaleHelper.setLocale(this, "ko")
        } else {
            LocaleHelper.setLocale(this, (getPrefer("LANGUAGE").toString()))
        }
        super.onCreate(savedInstanceState)

        performDataBinding()
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    fun putPrefer(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    /**
     * get value prefer
     * @param key get value by access name
     * @return empty by default value
     */
    fun getPrefer(key: String) = sharedPreference.getString(key, "ko")

    fun remove(key: String) = sharedPreference.edit().remove(key).commit()

    fun clear() = sharedPreference.edit().clear().commit()

    fun contain(key: String) : Boolean{
        return sharedPreference.contains(key)
    }
}