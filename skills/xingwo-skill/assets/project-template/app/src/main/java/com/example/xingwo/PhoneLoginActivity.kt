package com.example.xingwo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.xingwo.data.FakeData
import com.example.xingwo.data.SessionStore
import com.example.xingwo.ui.XingWoPageContainer
import com.example.xingwo.ui.screens.PhoneLoginScreen
import com.example.xingwo.ui.theme.XingWoTheme

class PhoneLoginActivity : ComponentActivity() {
    private var selectedCountryIndex by mutableIntStateOf(0)
    private var selectedCountry by mutableStateOf(FakeData.countries.first())
    private val sessionStore by lazy { SessionStore(this) }

    private val countryPickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val index = result.data?.getIntExtra(CountryPickerActivity.EXTRA_COUNTRY_INDEX, 0) ?: 0
            selectedCountryIndex = index.coerceIn(0, FakeData.countries.lastIndex)
            selectedCountry = FakeData.countries[selectedCountryIndex]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    PhoneLoginScreen(
                        country = selectedCountry,
                        onBack = { finish() },
                        onOpenCountryPicker = {
                            countryPickerLauncher.launch(
                                Intent(this@PhoneLoginActivity, CountryPickerActivity::class.java).apply {
                                    putExtra(CountryPickerActivity.EXTRA_COUNTRY_INDEX, selectedCountryIndex)
                                },
                            )
                        },
                        onLoginSuccess = {
                            sessionStore.setLoggedIn(true)
                            startActivity(
                                Intent(this@PhoneLoginActivity, MainActivity::class.java).apply {
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
