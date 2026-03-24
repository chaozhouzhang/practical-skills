package com.example.xinqingwu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.xinqingwu.data.FakeData
import com.example.xinqingwu.ui.screens.DivinationZoneDetailScreen
import com.example.xinqingwu.ui.XinQingWuPageContainer
import com.example.xinqingwu.ui.screens.FortuneDetailScreen
import com.example.xinqingwu.ui.screens.HealingNightDetailScreen
import com.example.xinqingwu.ui.screens.HotRoomsDetailScreen
import com.example.xinqingwu.ui.screens.SoulmateDetailScreen
import com.example.xinqingwu.ui.screens.SynastryDetailScreen
import com.example.xinqingwu.ui.screens.TarotDetailScreen
import com.example.xinqingwu.ui.screens.TreeHoleDetailScreen
import com.example.xinqingwu.ui.screens.ZodiacDetailScreen
import com.example.xinqingwu.ui.theme.XinQingWuTheme
import kotlin.random.Random

class FortuneDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
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
            XinQingWuTheme {
                XinQingWuPageContainer {
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
            XinQingWuTheme {
                XinQingWuPageContainer {
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

class SynastryDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
                    SynastryDetailScreen(onBack = { finish() })
                }
            }
        }
    }
}

class SoulmateDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
                    SoulmateDetailScreen(onBack = { finish() })
                }
            }
        }
    }
}

class HotRoomsDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
                    HotRoomsDetailScreen(onBack = { finish() })
                }
            }
        }
    }
}

class DivinationZoneDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
                    DivinationZoneDetailScreen(onBack = { finish() })
                }
            }
        }
    }
}

class HealingNightDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
                    HealingNightDetailScreen(onBack = { finish() })
                }
            }
        }
    }
}

class ZodiacDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
                    ZodiacDetailScreen(onBack = { finish() })
                }
            }
        }
    }
}
