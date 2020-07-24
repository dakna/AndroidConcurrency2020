package com.example.androidconcurrency2020

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidconcurrency2020.databinding.ActivityMainBinding
import kotlin.concurrent.thread
import kotlin.random.Random

const val DIE_VIEW_INDEX_KEY = "dieViewIndexKey"

const val DIE_DRAWABLE_INDEX_KEY = "dieDrawableIndexKey"


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var imageViews: Array<ImageView>

    val drawables = arrayOf(
        R.drawable.die_1,
        R.drawable.die_2,
        R.drawable.die_3,
        R.drawable.die_4,
        R.drawable.die_5,
        R.drawable.die_6
    )

    val handler = Handler(Looper.getMainLooper()) {
        val drawable = drawables[it.data.getInt(DIE_DRAWABLE_INDEX_KEY)]
        val view = imageViews[it.data.getInt(DIE_VIEW_INDEX_KEY)]
        view.setImageResource(drawable)
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding for view object references
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageViews = arrayOf(binding.die1, binding.die2, binding.die3, binding.die4, binding.die5)

        binding.rollButton.setOnClickListener { rollTheDice() }

    }

    /**
     * Run some code
     */
    private fun rollTheDice() {

        thread(start = true) {
            for (i in 1..20) {
                for (dieIndex in imageViews.indices) {
                    val dieNumber = getDieValue()
                    val bundle = Bundle().apply {
                        putInt(DIE_VIEW_INDEX_KEY, dieIndex)
                        putInt(DIE_DRAWABLE_INDEX_KEY, dieNumber - 1 )
                    }
                    Message().also {
                        it.data = bundle
                        handler.sendMessage(it)
                    }
                }
                Thread.sleep(100)
            }
        }
    }

    /**
     * Get a random number from 1 to 6
     */
    private fun getDieValue(): Int {
        return Random.nextInt(1, 7)
    }

}
