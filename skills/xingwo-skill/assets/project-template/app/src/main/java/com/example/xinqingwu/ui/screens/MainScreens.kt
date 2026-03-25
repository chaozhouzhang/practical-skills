package com.example.xinqingwu.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xinqingwu.FortuneDetailActivity
import com.example.xinqingwu.HealingNightDetailActivity
import com.example.xinqingwu.HotRoomsDetailActivity
import com.example.xinqingwu.R
import com.example.xinqingwu.ChineseZodiacDetailActivity
import com.example.xinqingwu.ProfileEditActivity
import com.example.xinqingwu.SoulmateDetailActivity
import com.example.xinqingwu.SynastryDetailActivity
import com.example.xinqingwu.TarotDetailActivity
import com.example.xinqingwu.TreeHoleDetailActivity
import com.example.xinqingwu.ZodiacDetailActivity
import com.example.xinqingwu.DivinationZoneDetailActivity
import com.example.xinqingwu.data.DailyFortuneGenerator
import com.example.xinqingwu.data.FakeData
import com.example.xinqingwu.data.UserProfile
import com.example.xinqingwu.data.UserProfileStore
import com.example.xinqingwu.model.BottomTab
import com.example.xinqingwu.PrivacyActivity
import com.example.xinqingwu.ui.components.AvatarBubble
import com.example.xinqingwu.ui.components.SectionTitle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun MainScreen(onExit: () -> Unit) {
    var selectedTab by remember { mutableStateOf(BottomTab.Home) }
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            containerColor = Color(0xFF13233B),
            title = {
                Text(
                    text = stringResource(R.string.logout_confirm_title),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.logout_confirm_message),
                    color = Color(0xFFB7C8D8),
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        onExit()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF67F1E4)),
                ) {
                    Text(stringResource(R.string.logout_confirm_action), color = Color(0xFF071725))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(stringResource(R.string.logout_cancel_action), color = Color.White)
                }
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF081325)),
    ) {
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                BottomTab.Home -> HomeScreen()
                BottomTab.Companion -> CompanionScreen()
                BottomTab.Profile -> ProfileScreen()
            }
        }
        BottomBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
        )
    }
}

@Composable
private fun HomeScreen() {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 52.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            DailyFortuneCard(
                onClick = {
                    context.startActivity(Intent(context, FortuneDetailActivity::class.java))
                },
            )
        }
        item {
            FeatureGrid(
                onCardClick = {
                    when (it.titleRes) {
                        R.string.home_card_tree_hole_title -> context.startActivity(Intent(context, TreeHoleDetailActivity::class.java))
                        R.string.home_card_tarot_title -> context.startActivity(Intent(context, TarotDetailActivity::class.java))
                        R.string.home_card_synastry_title -> context.startActivity(Intent(context, SynastryDetailActivity::class.java))
                        R.string.home_card_soulmate_title -> context.startActivity(Intent(context, SoulmateDetailActivity::class.java))
                        else -> context.openDetail(buildRecommendationDetail(context, it))
                    }
                },
            )
        }
        item { FeatureChipsRow(onChipClick = { context.openDetail(buildChipDetail(context, it)) }) }
        item { BannerCard(onClick = { context.startActivity(Intent(context, ZodiacDetailActivity::class.java)) }) }
        item { ChineseZodiacBannerCard(onClick = { context.startActivity(Intent(context, ChineseZodiacDetailActivity::class.java)) }) }
        item { SectionTitle(stringResource(R.string.section_today_topics), stringResource(R.string.action_refresh)) }
        item { TrendTopicRow(onTopicClick = { context.openDetail(buildTrendTopicDetail(context, it)) }) }
        item { SectionTitle(stringResource(R.string.section_soul_match), stringResource(R.string.action_view_all)) }
        item { MatchProfileRow(onProfileClick = { context.openDetail(buildMatchProfileDetail(context, it)) }) }
    }
}

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AvatarBubble(stringResource(R.string.avatar_short_name), modifier = Modifier.size(50.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.display_name), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 21.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                MiniBadge(stringResource(R.string.profile_tag_openhearted))
                MiniBadge(stringResource(R.string.profile_tag_sagittarius))
            }
        }
        Text(stringResource(R.string.icon_food), fontSize = 22.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Text(stringResource(R.string.icon_trophy), fontSize = 22.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Text(stringResource(R.string.icon_home), fontSize = 22.sp)
    }
}

@Composable
private fun PromptBubbleRow() {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        FakeData.promptBubbles.forEach { item ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF37454E))
                    .border(1.dp, Color(0xFF667984), RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
            ) {
                Text(stringResource(item.textRes), color = Color(0xFFF4F8FF), fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun MiniBadge(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFF944D))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Text(text, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun DailyFortuneCard(onClick: () -> Unit) {
    val context = LocalContext.current
    val fortune = DailyFortuneGenerator.generate(context)
    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF314A4D)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(stringResource(R.string.section_today_fortune), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(stringResource(R.string.icon_trend_up), color = Color(0xFFB6CBD0), fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.number_value, fortune.score), color = Color(0xFFFFE082), fontSize = 54.sp, fontWeight = FontWeight.Black)
                    Text(fortune.suggestion, color = Color(0xFFE3EEEF), fontSize = 15.sp, lineHeight = 22.sp)
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    fortune.bars.forEach { item ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height((item.value * 0.9f).dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(item.color),
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(stringResource(R.string.number_value, item.value), color = item.color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(stringResource(item.labelRes), color = Color(0xFFC7D2D9), fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureGrid(onCardClick: (com.example.xinqingwu.model.RecommendationCard) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        FakeData.homeCards.forEach { item ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clickable { onCardClick(item) },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.linearGradient(listOf(item.accent, item.accentEnd)))
                        .padding(14.dp),
                ) {
                    Column {
                        Text(stringResource(item.titleRes), color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(stringResource(item.subtitleRes), color = Color(0xFFF1F5FA), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureChipsRow(onChipClick: (com.example.xinqingwu.model.FeatureChip) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(FakeData.chips.size) { index ->
            val chip = FakeData.chips[index]
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(chip.color)
                    .clickable { onChipClick(chip) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Text(stringResource(chip.titleRes), color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun BannerCard(onClick: () -> Unit) {
    TopicBannerCard(
        title = stringResource(R.string.banner_title),
        subtitle = stringResource(R.string.banner_subtitle),
        colors = listOf(Color(0xFF102B9B), Color(0xFF233ED7)),
        icon = stringResource(R.string.icon_star),
        onClick = onClick,
    )
}

@Composable
private fun ChineseZodiacBannerCard(onClick: () -> Unit) {
    TopicBannerCard(
        title = stringResource(R.string.chinese_zodiac_banner_title),
        subtitle = stringResource(R.string.chinese_zodiac_banner_subtitle),
        colors = listOf(Color(0xFF5A2A14), Color(0xFFB65B1D)),
        icon = stringResource(R.string.icon_sparkles),
        onClick = onClick,
    )
}

@Composable
private fun TopicBannerCard(
    title: String,
    subtitle: String,
    colors: List<Color>,
    icon: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(92.dp)
                .background(Brush.horizontalGradient(colors))
                .padding(horizontal = 22.dp, vertical = 16.dp),
        ) {
            Column {
                Text(title, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
                Text(subtitle, color = Color(0xFFF6F8FE), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Text(icon, color = Color(0xFFFFEE8B), fontSize = 34.sp, modifier = Modifier.align(Alignment.CenterEnd))
        }
    }
}

@Composable
private fun TrendTopicRow(onTopicClick: (com.example.xinqingwu.model.TrendTopic) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(FakeData.trendTopics.size) { index ->
            val topic = FakeData.trendTopics[index]
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(114.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(topic.accent, topic.accent.copy(alpha = 0.45f), Color(0xFF14243D)),
                        ),
                    )
                    .clickable { onTopicClick(topic) }
                    .padding(16.dp),
            ) {
                Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()) {
                    Text(stringResource(topic.titleRes), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(stringResource(topic.subtitleRes), color = Color(0xFFDDE8F4), fontSize = 14.sp, lineHeight = 20.sp)
                }
            }
        }
    }
}

@Composable
private fun MatchProfileRow(onProfileClick: (com.example.xinqingwu.model.MatchProfile) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(FakeData.matchProfiles.size) { index ->
            val profile = FakeData.matchProfiles[index]
            Card(
                modifier = Modifier
                    .width(230.dp)
                    .clickable { onProfileClick(profile) },
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF15263B)),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AvatarBubble(stringResource(profile.nameRes), modifier = Modifier.size(46.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(profile.nameRes), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(stringResource(profile.signRes), color = Color(0xFF9DB0C5), fontSize = 13.sp)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(profile.accent)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                        ) {
                            Text(stringResource(R.string.percent_value, profile.compatibility), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(stringResource(profile.summaryRes), color = Color(0xFFD6E0EA), fontSize = 14.sp, lineHeight = 20.sp)
                }
            }
        }
    }
}

@Composable
private fun CompanionScreen() {
    val context = LocalContext.current
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item { BannerCard(onClick = { context.startActivity(Intent(context, ZodiacDetailActivity::class.java)) }) }
        item { SectionTitle(title = stringResource(R.string.section_recommended_companion), action = stringResource(R.string.action_view_more)) }
        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                userScrollEnabled = false,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(450.dp),
            ) {
                items(FakeData.companionCards) { item ->
                    Card(
                        modifier = Modifier
                            .height(210.dp)
                            .clickable { context.openDetail(buildCompanionCardDetail(context, item)) },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = item.accent),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = stringResource(item.titleRes),
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                            )
                            if (item.onlineCount > 0) {
                                Text(stringResource(R.string.people_online, item.onlineCount), color = Color(0xFFE8EEF5), fontSize = 14.sp)
                            } else {
                                Text(stringResource(R.string.divination_price, item.price ?: 0), color = Color(0xFFFFE28C), fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
        item { SectionTitle(title = stringResource(R.string.section_companion_zone)) }
        item {
            CompanionSectionRow(
                onSectionClick = {
                    when (it.titleRes) {
                        R.string.companion_section_1_title -> context.startActivity(Intent(context, HotRoomsDetailActivity::class.java))
                        R.string.companion_section_2_title -> context.startActivity(Intent(context, DivinationZoneDetailActivity::class.java))
                        R.string.companion_section_3_title -> context.startActivity(Intent(context, HealingNightDetailActivity::class.java))
                        else -> context.openDetail(buildCompanionSectionDetail(context, it))
                    }
                },
            )
        }
        item { SectionTitle(title = stringResource(R.string.section_live_rooms), action = stringResource(R.string.action_enter_square)) }
        item { CompanionRoomList(onRoomClick = { context.openDetail(buildCompanionRoomDetail(context, it)) }) }
    }
}

@Composable
private fun CompanionSectionRow(onSectionClick: (com.example.xinqingwu.model.CompanionSection) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(FakeData.companionSections.size) { index ->
            val item = FakeData.companionSections[index]
            Card(
                modifier = Modifier
                    .width(180.dp)
                    .clickable { onSectionClick(item) },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF12243B)),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(stringResource(item.titleRes), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(item.descriptionRes), color = Color(0xFF97ADC2), fontSize = 13.sp, lineHeight = 18.sp)
                }
            }
        }
    }
}

@Composable
private fun CompanionRoomList(onRoomClick: (com.example.xinqingwu.model.CompanionRoom) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FakeData.companionRooms.forEach { room ->
            Card(
                modifier = Modifier.clickable { onRoomClick(room) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = room.accent),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AvatarBubble(stringResource(room.hostRes), modifier = Modifier.size(42.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(room.titleRes), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(stringResource(R.string.host_name, stringResource(room.hostRes)), color = Color(0xFFE2E9F2), fontSize = 13.sp)
                        }
                        Text(stringResource(R.string.online_count, room.onlineCount), color = Color(0xFFFFE591), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        room.tags.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(99.dp))
                                    .background(Color.White.copy(alpha = 0.16f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                            ) {
                                Text(stringResource(tag), color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var profile by remember { mutableStateOf(UserProfileStore.getProfile(context)) }

    DisposableEffect(lifecycleOwner, context) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                profile = UserProfileStore.getProfile(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 52.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Card(
                modifier = Modifier.clickable { context.startActivity(Intent(context, ProfileEditActivity::class.java)) },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF182843)),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AvatarBubble(profile.nickname.take(1), modifier = Modifier.size(56.dp))
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(profile.nickname, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text(stringResource(R.string.profile_personal_card_subtitle), color = Color(0xFF9EB2C8), fontSize = 14.sp)
                        }
                        Text(stringResource(R.string.icon_sparkles), fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    ProfileSummaryRow(profile = profile)
                }
            }
        }
        item { SectionTitle(stringResource(R.string.section_my_features)) }
        item {
            FakeData.profileMenus.forEach { item ->
                ProfileAction(
                    titleRes = item.titleRes,
                    subtitleRes = item.subtitleRes,
                    badgeRes = item.badgeRes,
                    onClick = {
                        when (item.titleRes) {
                            R.string.profile_menu_privacy_title -> context.startActivity(Intent(context, PrivacyActivity::class.java))
                            R.string.profile_menu_personal_title -> context.startActivity(Intent(context, ProfileEditActivity::class.java))
                            else -> context.openDetail(buildProfileMenuDetail(context, item))
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun ProfileSummaryRow(profile: UserProfile) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        ProfileSummaryPill(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.profile_field_nickname),
            value = profile.nickname,
        )
        ProfileSummaryPill(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.profile_field_gender),
            value = profile.gender,
        )
        ProfileSummaryPill(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.profile_field_birthday),
            value = profile.birthday,
        )
        ProfileSummaryPill(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.profile_field_zodiac),
            value = profile.zodiac,
        )
        ProfileSummaryPill(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.profile_field_chinese_zodiac),
            value = profile.chineseZodiac,
        )
    }
}

@Composable
private fun ProfileSummaryPill(modifier: Modifier = Modifier, label: String, value: String) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF243A5C)),
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp)) {
            Text(label, color = Color(0xFF9BB3C7), fontSize = 12.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun ProfileAction(titleRes: Int, subtitleRes: Int, badgeRes: Int? = null, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF13233B)),
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(titleRes), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(stringResource(subtitleRes), color = Color(0xFF96AFC3), fontSize = 14.sp)
            }
            if (badgeRes != null) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0xFFFF8B7A))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(stringResource(badgeRes), color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            } else {
                Text(stringResource(R.string.symbol_chevron_right), color = Color(0xFF96AFC3), fontSize = 18.sp)
            }
        }
    }
}


@Composable
private fun BottomBar(selectedTab: BottomTab, onTabSelected: (BottomTab) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0A1324))
            .navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        FakeData.tabs.forEach { tab ->
            val selected = tab == selectedTab
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { onTabSelected(tab) }
                    .padding(horizontal = 18.dp, vertical = 6.dp),
            ) {
                Text(
                    text = when (tab) {
                        BottomTab.Home -> stringResource(R.string.icon_planet)
                        BottomTab.Companion -> stringResource(R.string.icon_people)
                        BottomTab.Profile -> stringResource(R.string.icon_profile)
                    },
                    fontSize = 20.sp,
                )
                Text(
                    stringResource(tab.labelRes),
                    color = if (selected) Color(0xFF67F1E4) else Color(0xFF8C9BB0),
                    fontSize = 13.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                )
            }
        }
    }
}
