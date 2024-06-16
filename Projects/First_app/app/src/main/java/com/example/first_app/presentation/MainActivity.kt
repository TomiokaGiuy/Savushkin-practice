package com.example.first_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.first_app.Data.Repository.DatabaseRepository
import com.example.first_app.ui.theme.First_appTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = DatabaseRepository(context = this)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        setContent {
            First_appTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        activity = this,
                    )
                }
            }
        }
    }

}

