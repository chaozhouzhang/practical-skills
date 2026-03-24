package com.example.xinqingwu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xinqingwu.ui.XinQingWuPageContainer
import com.example.xinqingwu.ui.screens.LegalDocumentScreen
import com.example.xinqingwu.ui.theme.XinQingWuTheme

class TermsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
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
