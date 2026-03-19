package com.example.xingwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xingwo.data.FakeData
import com.example.xingwo.ui.XingWoPageContainer
import com.example.xingwo.ui.screens.FortuneDetailScreen
import com.example.xingwo.ui.screens.TarotDetailScreen
import com.example.xingwo.ui.screens.TreeHoleDetailScreen
import com.example.xingwo.ui.theme.XingWoTheme
import kotlin.random.Random

class FortuneDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    FortuneDetailScreen(onBack = { finish() })
                }
            }
        }
    }
}

class TreeHoleDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    TreeHoleDetailScreen(onBack = { finish() })
                }
            }
        }
    }
}

class TarotDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val card = FakeData.tarotCards.random(Random(System.currentTimeMillis()))
        val loveTrendRes = FakeData.tarotLoveTrends.random(Random(System.currentTimeMillis() + 7))
        enableEdgeToEdge()
        setContent {
            XingWoTheme {
                XingWoPageContainer {
                    TarotDetailScreen(
                        card = card,
                        loveTrendRes = loveTrendRes,
                        onBack = { finish() },
                    )
                }
            }
        }
    }
}
