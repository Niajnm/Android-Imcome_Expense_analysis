package com.example.incomeexpensemanager

import java.util.*

interface SelectDateListener {
    fun onDateSelected(date: Date)
    fun onCanceled()  
}