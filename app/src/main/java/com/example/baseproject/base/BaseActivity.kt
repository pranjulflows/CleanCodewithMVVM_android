package com.example.baseproject.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baseproject.data.resource.Resource.Companion.success
import com.example.baseproject.utils.PrefUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Response
import javax.inject.Inject

@ExperimentalCoroutinesApi
abstract class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var mPrefUtils: PrefUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showToast(data: Any?) {
        Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()

    }
}
