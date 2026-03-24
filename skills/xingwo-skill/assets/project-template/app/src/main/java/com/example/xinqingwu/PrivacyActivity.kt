package com.example.xinqingwu

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PrivacyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_privacy)

        findViewById<TextView>(R.id.privacyBackButton).setOnClickListener { finish() }
        findViewById<TextView>(R.id.privacyTitleView).text = getString(R.string.privacy_title)
        findViewById<TextView>(R.id.privacyContentView).text = getString(R.string.privacy_content)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.privacyRoot)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }
}
