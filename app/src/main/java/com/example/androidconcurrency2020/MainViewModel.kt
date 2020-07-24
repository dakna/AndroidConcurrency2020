package com.example.androidconcurrency2020

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _dieUpdate = MutableLiveData<Pair<Int,Int>>()
    val dieUpdate = _dieUpdate

    fun rollTheDice(range: IntRange) {

        for (dieIndex in range) {
            viewModelScope.launch {
                delay(dieIndex * 10L)
                for (i in 1..20) {
                    dieUpdate.value = Pair(dieIndex, getDieValue())
                    delay(100)
                }
            }
        }
    }

    private fun getDieValue(): Int {
        return Random.nextInt(1, 7)
    }
}