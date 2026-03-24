package com.example.xingwo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.xingwo.ui.XingWoPageContainer
import com.example.xingwo.ui.screens.SplashScreen
import com.example.xingwo.ui.theme.XingWoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    SplashScreen()
                }
            }
        }

        lifecycleScope.launch {
            delay(1400)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
