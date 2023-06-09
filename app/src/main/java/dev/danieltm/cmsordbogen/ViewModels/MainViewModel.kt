package dev.danieltm.cmsordbogen.ViewModels

import android.app.Activity
import android.content.Context
import android.os.Debug
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dev.danieltm.cmsordbogen.Models.BottomNavItem
import dev.danieltm.cmsordbogen.Models.PostModel
import dev.danieltm.cmsordbogen.Models.Repositories.PostRepository
import dev.danieltm.cmsordbogen.utilities.IPostRepository
import dev.danieltm.cmsordbogen.utilities.PostsService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel : ViewModel(){

    var postsService = PostsService.create()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _allPostsList = MutableStateFlow(emptyList<PostModel>())
    val allPostsList = _allPostsList.asStateFlow()


    init {
        loadRecentPosts()

        runBlocking {
            launch { getPostsTemp() }
        }
    }

    fun loadRecentPosts(){
        // Simulate delay for loading
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000L)
            _isLoading.value = false
            getPostsTemp()
        }

    }

    fun getNavList() : List<BottomNavItem>
    {
        var navItems = listOf(
            BottomNavItem(
                name = "HJEM",
                route = "home",
                icon = Icons.Default.Home
            ),
            BottomNavItem(
                name = "",
                route = "create",
                icon = Icons.Default.AddCircle
            ),
            BottomNavItem(
                name = "INDLÆG",
                route = "posts",
                icon = Icons.Default.Menu,
                badgeCount = 7
            )
        )
        return navItems
    }

    suspend fun getPostsTemp()
    {
        _allPostsList.value = postsService.getAllPosts()
    }
}