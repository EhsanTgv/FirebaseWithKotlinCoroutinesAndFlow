package com.taghavi.firebasewithkotlincoroutinesandflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: PostsRepository) : ViewModel() {

    fun getAllPosts() = repository.getAllPosts()

    fun addPost(post: Post) = repository.addPost(post)
}