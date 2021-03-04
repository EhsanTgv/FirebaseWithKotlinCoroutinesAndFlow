package com.taghavi.firebasewithkotlincoroutinesandflow

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.taghavi.firebasewithkotlincoroutinesandflow.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is State.Success -> {
                    val postText = state.data.joinToString("\n") {
                        "${it.postContent} ~ ${it.postAuthor}"
                    }

                    binding.textPostContent.text = postText
                }

                is State.Failed -> Toast.makeText(this, "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}