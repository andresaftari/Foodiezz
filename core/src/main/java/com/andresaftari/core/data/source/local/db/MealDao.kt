package com.andresaftari.core.data.source.local.db

import androidx.room.*
import com.andresaftari.core.data.source.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category WHERE isFavorited = 1")
    fun getFavoritedMeal(): Flow<List<CategoryEntity>>

    @Update
    fun updateFavoritedMeal(categoryEntity: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(listCategory: List<CategoryEntity>)
}