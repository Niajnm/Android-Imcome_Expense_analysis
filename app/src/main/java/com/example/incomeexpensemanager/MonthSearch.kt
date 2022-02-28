package com.example.incomeexpensemanager

import RoomDatabase.DisplayItem
import RoomDatabase.User

interface MonthSearch {
    fun monthYearpass(items: List<DisplayItem>)

    fun monthYearpassUser(items: List<User>)
}