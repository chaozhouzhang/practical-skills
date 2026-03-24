package com.example.xingwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xingwo.ui.XingWoPageContainer
import com.example.xingwo.ui.screens.MainScreen
import com.example.xingwo.ui.theme.XingWoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    MainScreen(onExit = ::finish)
                }
            }
        }
    }
}
