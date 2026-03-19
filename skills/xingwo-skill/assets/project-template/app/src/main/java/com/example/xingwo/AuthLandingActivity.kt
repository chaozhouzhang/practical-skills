package com.example.xingwo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xingwo.ui.XingWoPageContainer
import com.example.xingwo.ui.screens.AuthLandingScreen
import com.example.xingwo.ui.theme.XingWoTheme

class AuthLandingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    AuthLandingScreen(
                        onPhoneLogin = {
                            startActivity(Intent(this@AuthLandingActivity, PhoneLoginActivity::class.java))
                        },
                        onTermsClick = {
                            startActivity(Intent(this@AuthLandingActivity, TermsActivity::class.java))
                        },
                        onPrivacyClick = {
                            startActivity(Intent(this@AuthLandingActivity, PrivacyActivity::class.java))
                        },
                    )
                }
            }
        }
    }
}
