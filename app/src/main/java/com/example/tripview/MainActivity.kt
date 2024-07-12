package com.example.tripview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.tripview.view.PexelsSearchScreen
import com.example.tripview.viewmodel.PexelsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: PexelsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PexelsSearchScreen(viewModel = viewModel)
        }
    }
}