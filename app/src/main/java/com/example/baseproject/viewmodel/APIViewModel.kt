package com.example.baseproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.data.ProductResponse
import com.example.baseproject.data.resource.Resource
import com.example.baseproject.reposotory.APIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class APIViewModel @Inject constructor(
    private val repository: APIRepository,
) : ViewModel() {

    val getProductListResponse: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    fun getProductList() {
        viewModelScope.launch {
            repository.getProductList().onEach { state ->
                getProductListResponse.value = state
            }.launchIn(viewModelScope)
        }
    }

}