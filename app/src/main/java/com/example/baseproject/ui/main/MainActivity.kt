package com.example.baseproject.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.baseproject.R
import com.example.baseproject.base.BaseActivity
import com.example.baseproject.data.resource.Status
import com.example.baseproject.databinding.ActivityMainBinding
import com.example.baseproject.utils.asString
import com.example.baseproject.utils.disable
import com.example.baseproject.utils.enable
import com.example.baseproject.viewmodel.APIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adaptor: ProductAdaptor
    private val viewModel: APIViewModel by viewModels()

    //ViewAnimator
    private val dataChild = 0
    private val loaderChild = 1
    private val noDataChild = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setClicks()
        setObservers()
    }

    private fun setClicks() {
        binding.btnGetData.setOnClickListener(this)
    }

    private fun initViews() {
        adaptor = ProductAdaptor()
        binding.rvProduct.adapter = adaptor
        binding.btnGetData.disable()
        viewModel.getProductList()
    }

    private fun setObservers() {
        viewModel.getProductListResponse.observe(this) { state ->
            if (state.status != Status.LOADING) binding.btnGetData.enable()
            when (state.status) {
                Status.LOADING -> {
                    binding.viewAnimator.displayedChild = loaderChild
                }

                Status.SUCCESS -> {
                    if (!state.data?.filterNotNull().isNullOrEmpty()) {
                        binding.viewAnimator.displayedChild = dataChild
                        adaptor.setDataList(ArrayList(state?.data?.filterNotNull()!!))
                    } else {
                        binding.viewAnimator.displayedChild = noDataChild
                        binding.txtNoData.text = R.string.no_data.asString()
                    }
                }

                Status.ERROR -> {
                    val error = state?.error
                    binding.viewAnimator.displayedChild = noDataChild
                    binding.txtNoData.text = error?.errorMessage
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnGetData -> {
                binding.btnGetData.disable()
                viewModel.getProductList()
            }
        }
    }
}