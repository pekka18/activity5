package com.dignos.animal.activity5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dignos.animal.activity5.databinding.ActivityMainBinding
import android.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.permission.setOnClickListener {
            val intent = Intent( this, Permission::class.java)
            startActivity(intent)
        }
    }
}