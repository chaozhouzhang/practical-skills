package com.example.xingwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xingwo.ui.XingWoPageContainer
import com.example.xingwo.ui.screens.LegalDocumentScreen
import com.example.xingwo.ui.theme.XingWoTheme

class PrivacyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    LegalDocumentScreen(
                        titleRes = R.string.privacy_title,
                        contentRes = R.string.privacy_content,
                        onBack = { finish() },
                    )
                }
            }
        }
    }
}
