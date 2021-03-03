package com.taghavi.firebasewithkotlincoroutinesandflow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, MainViewModelFactory())
            .get(MainViewModel::class.java)
    }

    private suspend fun loadPosts() {
        viewModel.getAllPosts().collect { state ->
            when (state) {
                is State.Loading -> {
                    showToast("Loading")
                }

                is State.Success -> {
                    val postText = state.data.joinToString("\n") {
                        "${it.postContent} ~ ${it.postAuthor}"
                    }

                    binding.textPostContent.text = postText
                }

                is State.Failed -> showToast("Failed! ${state.message}")
            }
        }
    }
}