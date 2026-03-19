package com.example.xingwo

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xingwo.model.DetailPageContent
import com.example.xingwo.ui.XingWoPageContainer
import com.example.xingwo.ui.screens.DetailScreen
import com.example.xingwo.ui.theme.XingWoTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = intent.detailContent() ?: run {
            finish()
            return
        }

        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    DetailScreen(
                        content = content,
                        onBack = { finish() },
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_DETAIL_CONTENT = "detail_content"

        fun createIntent(context: Context, content: DetailPageContent): Intent {
            return Intent(context, DetailActivity::class.java).putExtra(EXTRA_DETAIL_CONTENT, content)
        }
    }
}

private fun Intent.detailContent(): DetailPageContent? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(DetailActivity.EXTRA_DETAIL_CONTENT, DetailPageContent::class.java)
    } else {
        @Suppress("DEPRECATION")
        getSerializableExtra(DetailActivity.EXTRA_DETAIL_CONTENT) as? DetailPageContent
    }
}
