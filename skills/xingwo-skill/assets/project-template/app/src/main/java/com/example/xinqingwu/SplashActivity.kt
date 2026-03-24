package com.example.xinqingwu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.xinqingwu.ui.XinQingWuPageContainer
import com.example.xinqingwu.ui.screens.SplashScreen
import com.example.xinqingwu.ui.theme.XinQingWuTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
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
