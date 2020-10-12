package com.andresaftari.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andresaftari.core.data.source.local.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class MealDB : RoomDatabase() {
    abstract fun mealDao(): MealDao
}