package com.example.androidconcurrency2020

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidconcurrency2020.databinding.ActivityMainBinding
import kotlin.concurrent.thread

const val DIE_INDEX_KEY = "die_index_key"
const val DIE_VALUE_KEY = "die_value_key"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageViews: Array<ImageView>

    private lateinit var viewModel: MainViewModel

    private val drawables = arrayOf(
        R.drawable.die_1, R.drawable.die_2,
        R.drawable.die_3, R.drawable.die_4,
        R.drawable.die_5, R.drawable.die_6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Initialize view binding for view object references
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageViews = arrayOf(binding.die1, binding.die2, binding.die3, binding.die4, binding.die5)

        binding.rollButton.setOnClickListener { viewModel.rollTheDice(imageViews.indices) }

        viewModel.dieUpdate.observe(this, Observer {
            imageViews[it.first].setImageResource(drawables[it.second-1])
        })

    }
}
