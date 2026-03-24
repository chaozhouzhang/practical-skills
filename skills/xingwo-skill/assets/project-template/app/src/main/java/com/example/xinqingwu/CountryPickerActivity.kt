package com.example.xinqingwu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xinqingwu.ui.XinQingWuPageContainer
import com.example.xinqingwu.ui.screens.CountryPickerScreen
import com.example.xinqingwu.ui.theme.XinQingWuTheme

class CountryPickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val selectedIndex = intent.getIntExtra(EXTRA_COUNTRY_INDEX, 0)

        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
                    CountryPickerScreen(
                        selectedIndex = selectedIndex,
                        onBack = { finish() },
                        onSelectCountry = { index ->
                            setResult(
                                Activity.RESULT_OK,
                                Intent().putExtra(EXTRA_COUNTRY_INDEX, index),
                            )
                            finish()
                        },
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_COUNTRY_INDEX = "extra_country_index"
    }
}
