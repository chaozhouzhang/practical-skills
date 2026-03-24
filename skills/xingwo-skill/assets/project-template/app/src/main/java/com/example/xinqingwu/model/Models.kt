package com.example.xinqingwu.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.xinqingwu.R
import java.io.Serializable

enum class BottomTab(@StringRes val labelRes: Int) {
    Home(R.string.bottom_tab_home),
    Companion(R.string.bottom_tab_companion),
    Profile(R.string.bottom_tab_profile),
}

data class CountryCode(
    @StringRes val localNameRes: Int,
    @StringRes val englishNameRes: Int,
    @StringRes val codeRes: Int,
)

data class FortuneBar(
    @StringRes val labelRes: Int,
    val value: Int,
    val color: Color,
)

data class DailyFortune(
    val score: Int,
    @StringRes val suggestionRes: Int,
    val bars: List<FortuneBar>,
)

data class RecommendationCard(
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
    val accent: Color,
    val accentEnd: Color,
)

data class FeatureChip(
    @StringRes val titleRes: Int,
    val color: Color,
)

data class CompanionCard(
    @StringRes val titleRes: Int,
    val onlineCount: Int,
    val price: Int?,
    val accent: Color,
)

data class ProfileStat(
    @StringRes val labelRes: Int,
    @StringRes val valueRes: Int,
)

data class PromptBubble(
    @StringRes val textRes: Int,
)

data class TrendTopic(
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
    val accent: Color,
)

data class MatchProfile(
    @StringRes val nameRes: Int,
    @StringRes val signRes: Int,
    @StringRes val summaryRes: Int,
    val compatibility: Int,
    val accent: Color,
)

data class CompanionSection(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
)

data class CompanionRoom(
    @StringRes val titleRes: Int,
    @StringRes val hostRes: Int,
    val tags: List<Int>,
    val onlineCount: Int,
    val accent: Color,
)

data class ProfileMenu(
    @StringRes val titleRes: Int,
    @StringRes val subtitleRes: Int,
    @StringRes val badgeRes: Int? = null,
)

data class TreeHolePost(
    @StringRes val nameRes: Int,
    @StringRes val moodRes: Int,
    @StringRes val messageRes: Int,
    @StringRes val timeRes: Int,
)

data class TarotCard(
    @StringRes val nameRes: Int,
    @StringRes val keywordRes: Int,
    @StringRes val meaningRes: Int,
)

data class ZodiacProfile(
    @StringRes val nameRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val summaryRes: Int,
)

data class DetailSection(
    val title: String,
    val body: String,
) : Serializable

data class DetailPageContent(
    val category: String,
    val title: String,
    val subtitle: String,
    val highlights: ArrayList<String>,
    val sections: ArrayList<DetailSection>,
    val actionText: String,
) : Serializable
