package com.example.xingwo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xingwo.data.SessionStore
import com.example.xingwo.ui.XingWoPageContainer
import com.example.xingwo.ui.screens.MainScreen
import com.example.xingwo.ui.theme.XingWoTheme

class MainActivity : ComponentActivity() {
    private val sessionStore by lazy { SessionStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    MainScreen(
                        onLogout = {
                            sessionStore.setLoggedIn(false)
                            startActivity(
                                Intent(this@MainActivity, AuthLandingActivity::class.java).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                },
                            )
                            finish()
                        },
                    )
                }
            }
        }
    }
}
