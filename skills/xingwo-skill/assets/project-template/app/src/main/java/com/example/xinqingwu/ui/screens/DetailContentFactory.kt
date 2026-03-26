package com.example.xinqingwu.ui.screens

import android.content.Context
import com.example.xinqingwu.DetailActivity
import com.example.xinqingwu.R
import com.example.xinqingwu.data.IdealPartnerStore
import com.example.xinqingwu.data.UserProfileStore
import com.example.xinqingwu.model.CompanionCard
import com.example.xinqingwu.model.CompanionRoom
import com.example.xinqingwu.model.CompanionSection
import com.example.xinqingwu.model.DetailPageContent
import com.example.xinqingwu.model.DetailSection
import com.example.xinqingwu.model.FeatureChip
import com.example.xinqingwu.model.MatchProfile
import com.example.xinqingwu.model.ProfileMenu
import com.example.xinqingwu.model.ProfileStat
import com.example.xinqingwu.model.RecommendationCard
import com.example.xinqingwu.model.TrendTopic

internal fun Context.openDetail(content: DetailPageContent) {
    startActivity(DetailActivity.createIntent(this, content))
}

internal fun buildFortuneDetail(context: Context, score: Int, suggestion: String): DetailPageContent {
    return DetailPageContent(
        category = context.getString(R.string.section_today_fortune),
        title = context.getString(R.string.detail_fortune_title),
        subtitle = context.getString(R.string.detail_fortune_subtitle),
        highlights = arrayListOf(context.getString(R.string.detail_highlight_score, score)),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_rhythm), context.getString(R.string.detail_fortune_focus)),
            DetailSection(context.getString(R.string.detail_section_scene), context.getString(R.string.detail_fortune_scene)),
            DetailSection(context.getString(R.string.detail_section_suggestion), context.getString(R.string.detail_fortune_suggestion, suggestion)),
        ),
        actionText = context.getString(R.string.detail_action_start),
    )
}

internal fun buildRecommendationDetail(context: Context, item: RecommendationCard): DetailPageContent {
    val title = context.getString(item.titleRes)
    return buildFeatureStyleDetail(
        context = context,
        category = context.getString(R.string.detail_category_home_feature),
        title = title,
        subtitle = context.getString(R.string.detail_feature_subtitle, title),
        focus = context.getString(R.string.detail_feature_focus, title),
        scene = context.getString(R.string.detail_feature_scene),
        suggestion = context.getString(R.string.detail_feature_suggestion),
    )
}

internal fun buildChipDetail(context: Context, chip: FeatureChip): DetailPageContent {
    val title = context.getString(chip.titleRes)
    return buildFeatureStyleDetail(
        context = context,
        category = context.getString(R.string.detail_category_quick_entry),
        title = title,
        subtitle = context.getString(R.string.detail_chip_subtitle, title),
        focus = context.getString(R.string.detail_chip_focus, title),
        scene = context.getString(R.string.detail_chip_scene),
        suggestion = context.getString(R.string.detail_chip_suggestion),
    )
}

internal fun buildBannerDetail(context: Context): DetailPageContent {
    val title = context.getString(R.string.banner_title)
    return buildFeatureStyleDetail(
        context = context,
        category = context.getString(R.string.detail_category_banner),
        title = title,
        subtitle = context.getString(R.string.detail_banner_subtitle),
        focus = context.getString(R.string.detail_banner_focus),
        scene = context.getString(R.string.detail_banner_scene),
        suggestion = context.getString(R.string.detail_banner_suggestion),
    )
}

internal fun buildTrendTopicDetail(context: Context, topic: TrendTopic): DetailPageContent {
    val title = context.getString(topic.titleRes)
    val subtitle = context.getString(topic.subtitleRes)
    return DetailPageContent(
        category = context.getString(R.string.section_today_topics),
        title = title,
        subtitle = subtitle,
        highlights = arrayListOf(context.getString(R.string.detail_highlight_today_topic)),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_focus), context.getString(R.string.detail_topic_focus, title)),
            DetailSection(context.getString(R.string.detail_section_scene), context.getString(R.string.detail_topic_scene)),
            DetailSection(context.getString(R.string.detail_section_suggestion), context.getString(R.string.detail_topic_suggestion)),
        ),
        actionText = context.getString(R.string.detail_action_save),
    )
}

internal fun buildMatchProfileDetail(context: Context, profile: MatchProfile): DetailPageContent {
    val name = context.getString(profile.nameRes)
    val sign = context.getString(profile.signRes)
    val summary = context.getString(profile.summaryRes)
    return DetailPageContent(
        category = context.getString(R.string.section_soul_match),
        title = name,
        subtitle = summary,
        highlights = arrayListOf(
            context.getString(R.string.detail_highlight_compatibility, profile.compatibility),
            context.getString(R.string.detail_highlight_sign, sign),
        ),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_mode), context.getString(R.string.detail_match_mode, name, sign)),
            DetailSection(context.getString(R.string.detail_section_benefit), context.getString(R.string.detail_match_benefit, name)),
            DetailSection(context.getString(R.string.detail_section_suggestion), context.getString(R.string.detail_match_suggestion)),
        ),
        actionText = "",
    )
}

internal fun buildIdealPartnerMatchDetail(context: Context): DetailPageContent {
    val partner = IdealPartnerStore.getProfile(context)
    val self = UserProfileStore.getProfile(context)
    val baseProfile = FakeData.matchProfiles.first()
    val name = partner.nickname
    val sign = partner.zodiac
    val compatibility = baseProfile.compatibility
    return DetailPageContent(
        category = context.getString(R.string.section_soul_match),
        title = name,
        subtitle = context.getString(R.string.detail_ideal_partner_match_subtitle, self.nickname, partner.nickname),
        highlights = arrayListOf(
            context.getString(R.string.detail_highlight_compatibility, compatibility),
            context.getString(R.string.detail_highlight_sign, sign),
        ),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_mode), context.getString(R.string.detail_match_mode, name, sign)),
            DetailSection(context.getString(R.string.detail_section_benefit), context.getString(R.string.detail_match_benefit, name)),
            DetailSection(context.getString(R.string.detail_section_suggestion), context.getString(R.string.detail_match_suggestion)),
        ),
        actionText = "",
    )
}

internal fun buildCompanionCardDetail(context: Context, card: CompanionCard): DetailPageContent {
    val title = context.getString(card.titleRes)
    val highlights = arrayListOf<String>()
    if (card.onlineCount > 0) {
        highlights += context.getString(R.string.detail_highlight_online_count, card.onlineCount)
    }
    card.price?.let { highlights += context.getString(R.string.detail_highlight_price, it) }
    return DetailPageContent(
        category = context.getString(R.string.section_recommended_companion),
        title = title,
        subtitle = context.getString(R.string.detail_companion_card_subtitle),
        highlights = highlights,
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_mode), context.getString(R.string.detail_companion_card_mode, title)),
            DetailSection(context.getString(R.string.detail_section_scene), context.getString(R.string.detail_companion_card_scene)),
            DetailSection(context.getString(R.string.detail_section_next_step), context.getString(R.string.detail_companion_card_next)),
        ),
        actionText = context.getString(R.string.detail_action_join),
    )
}

internal fun buildCompanionSectionDetail(context: Context, section: CompanionSection): DetailPageContent {
    val title = context.getString(section.titleRes)
    val description = context.getString(section.descriptionRes)
    return DetailPageContent(
        category = context.getString(R.string.section_companion_zone),
        title = title,
        subtitle = description,
        highlights = arrayListOf(context.getString(R.string.detail_highlight_zone)),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_focus), context.getString(R.string.detail_section_focus_body, title)),
            DetailSection(context.getString(R.string.detail_section_benefit), context.getString(R.string.detail_section_benefit_body, title)),
            DetailSection(context.getString(R.string.detail_section_next_step), context.getString(R.string.detail_section_next_body)),
        ),
        actionText = context.getString(R.string.detail_action_explore),
    )
}

internal fun buildCompanionRoomDetail(context: Context, room: CompanionRoom): DetailPageContent {
    val title = context.getString(room.titleRes)
    val host = context.getString(room.hostRes)
    val tags = room.tags.joinToString(" / ") { context.getString(it) }
    return DetailPageContent(
        category = context.getString(R.string.section_live_rooms),
        title = title,
        subtitle = context.getString(R.string.detail_room_subtitle, host),
        highlights = arrayListOf(
            context.getString(R.string.detail_highlight_online_count, room.onlineCount),
            context.getString(R.string.detail_highlight_tags, tags),
        ),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_mode), context.getString(R.string.detail_room_mode, host)),
            DetailSection(context.getString(R.string.detail_section_scene), context.getString(R.string.detail_room_scene, tags)),
            DetailSection(context.getString(R.string.detail_section_suggestion), context.getString(R.string.detail_room_suggestion)),
        ),
        actionText = context.getString(R.string.detail_action_join_room),
    )
}

internal fun buildProfileOverviewDetail(context: Context): DetailPageContent {
    val name = context.getString(R.string.display_name)
    return DetailPageContent(
        category = context.getString(R.string.bottom_tab_profile),
        title = name,
        subtitle = context.getString(R.string.profile_subtitle),
        highlights = arrayListOf(context.getString(R.string.detail_highlight_profile)),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_status), context.getString(R.string.detail_profile_status)),
            DetailSection(context.getString(R.string.detail_section_focus), context.getString(R.string.detail_profile_focus)),
            DetailSection(context.getString(R.string.detail_section_next_step), context.getString(R.string.detail_profile_next)),
        ),
        actionText = context.getString(R.string.detail_action_review_profile),
    )
}

internal fun buildProfileStatDetail(context: Context, stat: ProfileStat): DetailPageContent {
    val title = context.getString(stat.labelRes)
    val value = context.getString(stat.valueRes)
    return DetailPageContent(
        category = context.getString(R.string.bottom_tab_profile),
        title = title,
        subtitle = context.getString(R.string.detail_stat_subtitle),
        highlights = arrayListOf(context.getString(R.string.detail_highlight_value, value)),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_status), context.getString(R.string.detail_stat_status, title, value)),
            DetailSection(context.getString(R.string.detail_section_benefit), context.getString(R.string.detail_stat_benefit, title)),
            DetailSection(context.getString(R.string.detail_section_next_step), context.getString(R.string.detail_stat_next)),
        ),
        actionText = context.getString(R.string.detail_action_keep_going),
    )
}

internal fun buildProfileMenuDetail(context: Context, menu: ProfileMenu): DetailPageContent {
    val title = context.getString(menu.titleRes)
    val subtitle = context.getString(menu.subtitleRes)
    val highlights = arrayListOf<String>()
    menu.badgeRes?.let { highlights += context.getString(R.string.detail_highlight_pending_count, context.getString(it)) }
    if (highlights.isEmpty()) highlights += context.getString(R.string.detail_highlight_function)
    return DetailPageContent(
        category = context.getString(R.string.section_my_features),
        title = title,
        subtitle = subtitle,
        highlights = highlights,
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_focus), context.getString(R.string.detail_menu_focus, title)),
            DetailSection(context.getString(R.string.detail_section_benefit), context.getString(R.string.detail_menu_benefit, title)),
            DetailSection(context.getString(R.string.detail_section_next_step), context.getString(R.string.detail_menu_next)),
        ),
        actionText = context.getString(R.string.detail_action_open_feature),
    )
}

internal fun buildCoupleZodiacDetail(context: Context): DetailPageContent {
    val self = UserProfileStore.getProfile(context)
    val partner = IdealPartnerStore.getProfile(context)
    return DetailPageContent(
        category = context.getString(R.string.section_my_features),
        title = context.getString(R.string.profile_menu_couple_zodiac_title),
        subtitle = context.getString(R.string.detail_couple_zodiac_subtitle, self.nickname, partner.nickname),
        highlights = arrayListOf(
            context.getString(R.string.detail_highlight_sign, self.zodiac),
            context.getString(R.string.detail_highlight_sign, partner.zodiac),
        ),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_couple_self_sign_title, self.nickname, self.zodiac), zodiacSummary(context, self.zodiac)),
            DetailSection(context.getString(R.string.detail_couple_partner_sign_title, partner.nickname, partner.zodiac), zodiacSummary(context, partner.zodiac)),
            DetailSection(
                context.getString(R.string.detail_couple_relation_title),
                context.getString(R.string.detail_couple_zodiac_relation_body, self.zodiac, partner.zodiac),
            ),
        ),
        actionText = "",
    )
}

internal fun buildCoupleChineseZodiacDetail(context: Context): DetailPageContent {
    val self = UserProfileStore.getProfile(context)
    val partner = IdealPartnerStore.getProfile(context)
    return DetailPageContent(
        category = context.getString(R.string.section_my_features),
        title = context.getString(R.string.profile_menu_couple_chinese_zodiac_title),
        subtitle = context.getString(R.string.detail_couple_chinese_zodiac_subtitle, self.nickname, partner.nickname),
        highlights = arrayListOf(
            context.getString(R.string.detail_highlight_chinese_zodiac, self.chineseZodiac),
            context.getString(R.string.detail_highlight_chinese_zodiac, partner.chineseZodiac),
        ),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_couple_self_zodiac_title, self.nickname, self.chineseZodiac), chineseZodiacSummary(context, self.chineseZodiac)),
            DetailSection(context.getString(R.string.detail_couple_partner_zodiac_title, partner.nickname, partner.chineseZodiac), chineseZodiacSummary(context, partner.chineseZodiac)),
            DetailSection(
                context.getString(R.string.detail_couple_relation_title),
                context.getString(R.string.detail_couple_chinese_zodiac_relation_body, self.chineseZodiac, partner.chineseZodiac),
            ),
        ),
        actionText = "",
    )
}

private fun zodiacSummary(context: Context, zodiac: String): String {
    return FakeData.zodiacProfiles.firstOrNull { context.getString(it.nameRes) == zodiac }
        ?.let { context.getString(it.summaryRes) }
        ?: context.getString(R.string.detail_default_zodiac_summary, zodiac)
}

private fun chineseZodiacSummary(context: Context, chineseZodiac: String): String {
    return FakeData.chineseZodiacProfiles.firstOrNull { context.getString(it.nameRes) == chineseZodiac }
        ?.let { context.getString(it.summaryRes) }
        ?: context.getString(R.string.detail_default_chinese_zodiac_summary, chineseZodiac)
}

private fun buildFeatureStyleDetail(
    context: Context,
    category: String,
    title: String,
    subtitle: String,
    focus: String,
    scene: String,
    suggestion: String,
): DetailPageContent {
    return DetailPageContent(
        category = category,
        title = title,
        subtitle = subtitle,
        highlights = arrayListOf(context.getString(R.string.detail_highlight_recommended)),
        sections = arrayListOf(
            DetailSection(context.getString(R.string.detail_section_focus), focus),
            DetailSection(context.getString(R.string.detail_section_scene), scene),
            DetailSection(context.getString(R.string.detail_section_suggestion), suggestion),
        ),
        actionText = context.getString(R.string.detail_action_start),
    )
}
