package com.example.xingwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xingwo.ui.XingWoPageContainer
import com.example.xingwo.ui.screens.LegalDocumentScreen
import com.example.xingwo.ui.theme.XingWoTheme

class TermsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    LegalDocumentScreen(
                        titleRes = R.string.terms_title,
                        contentRes = R.string.terms_content,
                        onBack = { finish() },
                    )
                }
            }
        }
    }
}
