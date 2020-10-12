package com.andresaftari.foodiezz.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.andresaftari.core.domain.usecase.IMealUseCase

class HomeViewModel(iMealUseCase: IMealUseCase) : ViewModel() {
    val category = iMealUseCase.getAllCategories().asLiveData()
}