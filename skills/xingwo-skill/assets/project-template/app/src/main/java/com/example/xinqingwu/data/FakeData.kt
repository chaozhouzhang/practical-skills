package com.example.xinqingwu.data

import androidx.compose.ui.graphics.Color
import com.example.xinqingwu.R
import com.example.xinqingwu.model.BottomTab
import com.example.xinqingwu.model.CompanionCard
import com.example.xinqingwu.model.CountryCode
import com.example.xinqingwu.model.DailyFortune
import com.example.xinqingwu.model.FeatureChip
import com.example.xinqingwu.model.FortuneBar
import com.example.xinqingwu.model.MatchProfile
import com.example.xinqingwu.model.CompanionRoom
import com.example.xinqingwu.model.CompanionSection
import com.example.xinqingwu.model.ProfileMenu
import com.example.xinqingwu.model.PromptBubble
import com.example.xinqingwu.model.RecommendationCard
import com.example.xinqingwu.model.TarotCard
import com.example.xinqingwu.model.TreeHolePost
import com.example.xinqingwu.model.TrendTopic
import com.example.xinqingwu.model.ZodiacProfile

object FakeData {
    val countries = listOf(
        CountryCode(R.string.country_taiwan, R.string.country_taiwan_en, R.string.country_code_taiwan),
        CountryCode(R.string.country_china, R.string.country_china_en, R.string.country_code_china),
        CountryCode(R.string.country_hong_kong, R.string.country_hong_kong_en, R.string.country_code_hong_kong),
        CountryCode(R.string.country_macao, R.string.country_macao_en, R.string.country_code_macao),
        CountryCode(R.string.country_malaysia, R.string.country_malaysia_en, R.string.country_code_malaysia),
        CountryCode(R.string.country_singapore, R.string.country_singapore_en, R.string.country_code_singapore),
        CountryCode(R.string.country_usa, R.string.country_usa_en, R.string.country_code_usa),
        CountryCode(R.string.country_australia, R.string.country_australia_en, R.string.country_code_australia),
    )

    val dailyFortune = DailyFortune(
        score = 90,
        suggestionRes = R.string.fortune_suggestion,
        bars = listOf(
            FortuneBar(R.string.fortune_love, 92, Color(0xFFFF78C1)),
            FortuneBar(R.string.fortune_career, 90, Color(0xFF9B86FF)),
            FortuneBar(R.string.fortune_wealth, 91, Color(0xFF68B9FF)),
            FortuneBar(R.string.fortune_social, 86, Color(0xFFFFC857)),
            FortuneBar(R.string.fortune_study, 88, Color(0xFFE778FF)),
        ),
    )

    val homeCards = listOf(
        RecommendationCard(
            titleRes = R.string.home_card_tree_hole_title,
            subtitleRes = R.string.home_card_tree_hole_subtitle,
            accent = Color(0xFF2F71C8),
            accentEnd = Color(0xFF1A284E),
        ),
        RecommendationCard(
            titleRes = R.string.home_card_tarot_title,
            subtitleRes = R.string.home_card_tarot_subtitle,
            accent = Color(0xFF9E4BFF),
            accentEnd = Color(0xFF5B219F),
        ),
        RecommendationCard(
            titleRes = R.string.home_card_synastry_title,
            subtitleRes = R.string.home_card_synastry_subtitle,
            accent = Color(0xFF4E67FF),
            accentEnd = Color(0xFF1A2F86),
        ),
        RecommendationCard(
            titleRes = R.string.home_card_soulmate_title,
            subtitleRes = R.string.home_card_soulmate_subtitle,
            accent = Color(0xFFFF7BD8),
            accentEnd = Color(0xFFFFA4B7),
        ),
    )

    val chips = listOf(
        FeatureChip(R.string.feature_chip_astrology, Color(0xFF4AD96E)),
        FeatureChip(R.string.feature_chip_ziwei, Color(0xFFB55DFF)),
        FeatureChip(R.string.feature_chip_calendar, Color(0xFFFFA53A)),
        FeatureChip(R.string.feature_chip_match, Color(0xFFFF7CAA)),
    )

    val promptBubbles = listOf(
        PromptBubble(R.string.prompt_1),
        PromptBubble(R.string.prompt_2),
        PromptBubble(R.string.prompt_3),
    )

    val trendTopics = listOf(
        TrendTopic(R.string.trend_topic_1_title, R.string.trend_topic_1_subtitle, Color(0xFF7354FF)),
        TrendTopic(R.string.trend_topic_2_title, R.string.trend_topic_2_subtitle, Color(0xFF2E8CCF)),
        TrendTopic(R.string.trend_topic_3_title, R.string.trend_topic_3_subtitle, Color(0xFFFF8AB8)),
    )

    val matchProfiles = listOf(
        MatchProfile(R.string.match_name_1, R.string.match_sign_1, R.string.match_summary_1, 95, Color(0xFF8E6BFF)),
        MatchProfile(R.string.match_name_2, R.string.match_sign_2, R.string.match_summary_2, 92, Color(0xFFFF84C2)),
        MatchProfile(R.string.match_name_3, R.string.match_sign_3, R.string.match_summary_3, 88, Color(0xFF61B8FF)),
    )

    val companionCards = listOf(
        CompanionCard(
            titleRes = R.string.companion_card_1_title,
            onlineCount = 16,
            price = null,
            accent = Color(0xFF40486A),
        ),
        CompanionCard(
            titleRes = R.string.companion_card_2_title,
            onlineCount = 0,
            price = 199,
            accent = Color(0xFF8FD4D8),
        ),
        CompanionCard(
            titleRes = R.string.companion_card_3_title,
            onlineCount = 0,
            price = 199,
            accent = Color(0xFF394B95),
        ),
        CompanionCard(
            titleRes = R.string.companion_card_4_title,
            onlineCount = 16,
            price = null,
            accent = Color(0xFF547E96),
        ),
    )

    val companionSections = listOf(
        CompanionSection(R.string.companion_section_1_title, R.string.companion_section_1_desc),
        CompanionSection(R.string.companion_section_2_title, R.string.companion_section_2_desc),
        CompanionSection(R.string.companion_section_3_title, R.string.companion_section_3_desc),
    )

    val companionRooms = listOf(
        CompanionRoom(
            R.string.room_1_title,
            R.string.room_1_host,
            listOf(R.string.tag_healing, R.string.tag_night_chat, R.string.tag_tree_hole),
            128,
            Color(0xFF3A4268),
        ),
        CompanionRoom(
            R.string.room_2_title,
            R.string.room_2_host,
            listOf(R.string.tag_tarot, R.string.tag_luck, R.string.tag_reading),
            76,
            Color(0xFF6D47C3),
        ),
        CompanionRoom(
            R.string.room_3_title,
            R.string.room_3_host,
            listOf(R.string.tag_sign, R.string.tag_love, R.string.tag_interaction),
            54,
            Color(0xFF275A92),
        ),
        CompanionRoom(
            R.string.room_4_title,
            R.string.room_4_host,
            listOf(R.string.tag_match, R.string.tag_companion, R.string.tag_heal),
            89,
            Color(0xFF4C7A88),
        ),
    )

    val profileMenus = listOf(
        ProfileMenu(R.string.profile_menu_personal_title, R.string.profile_menu_personal_subtitle),
        ProfileMenu(R.string.profile_menu_chart_title, R.string.profile_menu_chart_subtitle),
        ProfileMenu(R.string.profile_menu_saved_title, R.string.profile_menu_saved_subtitle),
        ProfileMenu(R.string.profile_menu_privacy_title, R.string.profile_menu_privacy_subtitle),
        ProfileMenu(R.string.profile_menu_settings_title, R.string.profile_menu_settings_subtitle),
    )

    val treeHolePosts = listOf(
        TreeHolePost(R.string.tree_hole_user_1, R.string.tree_hole_mood_1, R.string.tree_hole_message_1, R.string.tree_hole_time_1),
        TreeHolePost(R.string.tree_hole_user_2, R.string.tree_hole_mood_2, R.string.tree_hole_message_2, R.string.tree_hole_time_2),
        TreeHolePost(R.string.tree_hole_user_3, R.string.tree_hole_mood_3, R.string.tree_hole_message_3, R.string.tree_hole_time_3),
        TreeHolePost(R.string.tree_hole_user_4, R.string.tree_hole_mood_4, R.string.tree_hole_message_4, R.string.tree_hole_time_4),
        TreeHolePost(R.string.tree_hole_user_5, R.string.tree_hole_mood_5, R.string.tree_hole_message_5, R.string.tree_hole_time_5),
        TreeHolePost(R.string.tree_hole_user_6, R.string.tree_hole_mood_6, R.string.tree_hole_message_6, R.string.tree_hole_time_6),
        TreeHolePost(R.string.tree_hole_user_7, R.string.tree_hole_mood_7, R.string.tree_hole_message_7, R.string.tree_hole_time_7),
    )

    val tarotCards = listOf(
        TarotCard(R.string.tarot_card_1_name, R.string.tarot_card_1_keyword, R.string.tarot_card_1_meaning),
        TarotCard(R.string.tarot_card_2_name, R.string.tarot_card_2_keyword, R.string.tarot_card_2_meaning),
        TarotCard(R.string.tarot_card_3_name, R.string.tarot_card_3_keyword, R.string.tarot_card_3_meaning),
        TarotCard(R.string.tarot_card_4_name, R.string.tarot_card_4_keyword, R.string.tarot_card_4_meaning),
        TarotCard(R.string.tarot_card_5_name, R.string.tarot_card_5_keyword, R.string.tarot_card_5_meaning),
    )

    val tarotLoveTrends = listOf(
        R.string.tarot_love_trend_1,
        R.string.tarot_love_trend_2,
        R.string.tarot_love_trend_3,
        R.string.tarot_love_trend_4,
        R.string.tarot_love_trend_5,
    )

    val zodiacProfiles = listOf(
        ZodiacProfile(R.string.zodiac_aries_name, R.string.zodiac_aries_title, R.string.zodiac_aries_summary),
        ZodiacProfile(R.string.zodiac_taurus_name, R.string.zodiac_taurus_title, R.string.zodiac_taurus_summary),
        ZodiacProfile(R.string.zodiac_gemini_name, R.string.zodiac_gemini_title, R.string.zodiac_gemini_summary),
        ZodiacProfile(R.string.zodiac_cancer_name, R.string.zodiac_cancer_title, R.string.zodiac_cancer_summary),
        ZodiacProfile(R.string.zodiac_leo_name, R.string.zodiac_leo_title, R.string.zodiac_leo_summary),
        ZodiacProfile(R.string.zodiac_virgo_name, R.string.zodiac_virgo_title, R.string.zodiac_virgo_summary),
        ZodiacProfile(R.string.zodiac_libra_name, R.string.zodiac_libra_title, R.string.zodiac_libra_summary),
        ZodiacProfile(R.string.zodiac_scorpio_name, R.string.zodiac_scorpio_title, R.string.zodiac_scorpio_summary),
        ZodiacProfile(R.string.zodiac_sagittarius_name, R.string.zodiac_sagittarius_title, R.string.zodiac_sagittarius_summary),
        ZodiacProfile(R.string.zodiac_capricorn_name, R.string.zodiac_capricorn_title, R.string.zodiac_capricorn_summary),
        ZodiacProfile(R.string.zodiac_aquarius_name, R.string.zodiac_aquarius_title, R.string.zodiac_aquarius_summary),
        ZodiacProfile(R.string.zodiac_pisces_name, R.string.zodiac_pisces_title, R.string.zodiac_pisces_summary),
    )

    val tabs = listOf(BottomTab.Home, BottomTab.Companion, BottomTab.Profile)
}
