package com.c22ps099.relasiahelperapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps099.relasiahelperapp.R

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}