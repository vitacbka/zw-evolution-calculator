package com.evo.points

import androidx.lifecycle.ViewModel

class EvoDataViewModel : ViewModel() {
    private val dayInputs = mutableMapOf<Int, List<String>>()
    private val dayPoints = mutableMapOf<Int, Int>()

    fun getDayInputs(): MutableMap<Int, List<String>> = dayInputs
    fun getDayPoints(): MutableMap<Int, Int> = dayPoints
}