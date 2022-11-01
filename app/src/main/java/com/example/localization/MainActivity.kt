package com.example.localization

import android.os.Bundle
import com.example.localization.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.btnEnglish.setOnClickListener {
            putPrefer("LANGUAGE","en")
            finish()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
        }
        binding.btnKorean.setOnClickListener {
            putPrefer("LANGUAGE","ko")
            finish()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
        }
    }
}

