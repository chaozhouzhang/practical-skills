package com.example.xinqingwu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.xinqingwu.R
import com.example.xinqingwu.data.DailyFortuneGenerator
import com.example.xinqingwu.data.FakeData
import com.example.xinqingwu.data.TreeHoleStore
import com.example.xinqingwu.data.UserProfileStore
import com.example.xinqingwu.TreeHolePublishActivity
import com.example.xinqingwu.model.CompanionRoom
import com.example.xinqingwu.model.MatchProfile
import com.example.xinqingwu.model.TarotCard
import com.example.xinqingwu.model.ZodiacProfile
import com.example.xinqingwu.ui.components.AvatarBubble
import com.example.xinqingwu.ui.components.GradientPrimaryButton

@Composable
fun FortuneDetailScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val fortune = DailyFortuneGenerator.generate(context)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { DetailTopBar(title = stringResource(R.string.section_today_fortune), onBack = onBack) }
        item {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF17304C)),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(stringResource(R.string.section_today_fortune), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Column {
                            Text(stringResource(R.string.number_value, fortune.score), color = Color(0xFFFFE082), fontSize = 64.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF12243B)),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(stringResource(R.string.detail_section_suggestion), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(fortune.suggestion, color = Color(0xFFDCE7F0), fontSize = 15.sp, lineHeight = 24.sp)
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(stringResource(R.string.detail_section_rhythm), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                fortune.bars.forEach { item ->
                    FortuneMetricCard(
                        label = stringResource(item.labelRes),
                        value = item.value,
                        color = item.color,
                    )
                }
            }
        }
    }
}

@Composable
private fun FortuneMetricCard(label: String, value: Int, color: Color) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF13233B)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(label, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(stringResource(R.string.number_value, value), color = color, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color.White.copy(alpha = 0.10f)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth((value / 100f).coerceIn(0f, 1f))
                        .height(10.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(color),
                )
            }
        }
    }
}

@Composable
fun TreeHoleDetailScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val profile = UserProfileStore.getProfile(context)
    var userPosts by remember { mutableStateOf(TreeHoleStore.posts(context)) }

    DisposableEffect(lifecycleOwner, context) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                userPosts = TreeHoleStore.posts(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item { DetailTopBar(title = stringResource(R.string.home_card_tree_hole_title), onBack = onBack) }
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF17304C)),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(stringResource(R.string.tree_hole_intro_title), color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(R.string.tree_hole_intro_subtitle), color = Color(0xFFD5E2EE), fontSize = 14.sp, lineHeight = 22.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(stringResource(R.string.tree_hole_intro_support), color = Color(0xFF9DE7FF), fontSize = 14.sp, lineHeight = 22.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    GradientPrimaryButton(
                        text = stringResource(R.string.tree_hole_publish_entry),
                        onClick = { context.startActivity(android.content.Intent(context, TreeHolePublishActivity::class.java)) },
                    )
                }
            }
        }
        if (userPosts.isNotEmpty()) {
            item {
                Text(stringResource(R.string.tree_hole_latest_post_title), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            items(userPosts.size) { index ->
                TreeHolePostCard(
                    displayName = profile.nickname,
                    timeLabel = userPosts[index].timeLabel,
                    tagLabel = userPosts[index].tags.firstOrNull(),
                    message = userPosts[index].message,
                    containerColor = Color(0xFF14304A),
                )
            }
        }
        item {
            Text(stringResource(R.string.tree_hole_example_title), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        items(FakeData.treeHolePosts.size) { index ->
            val post = FakeData.treeHolePosts[index]
            TreeHolePostCard(
                displayName = profile.nickname,
                timeLabel = stringResource(R.string.tree_hole_example_time),
                tagLabel = stringResource(post.moodRes),
                message = stringResource(post.messageRes),
                containerColor = Color(0xFF12243B),
            )
        }
    }
}

@Composable
private fun TreeHolePostCard(
    displayName: String,
    timeLabel: String,
    tagLabel: String?,
    message: String,
    containerColor: Color,
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarBubble(displayName.take(1), modifier = Modifier.size(44.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(displayName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(timeLabel, color = Color(0xFF8FA7BC), fontSize = 12.sp)
                }
                tagLabel?.takeIf { it.isNotBlank() }?.let { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color(0xFF304F71))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                    ) {
                        Text(tag, color = Color(0xFF9DE7FF), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(message, color = Color(0xFFE4EDF6), fontSize = 15.sp, lineHeight = 23.sp)
        }
    }
}

@Composable
fun TarotDetailScreen(
    card: TarotCard,
    loveTrendRes: Int,
    onBack: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { DetailTopBar(title = stringResource(R.string.home_card_tarot_title), onBack = onBack) }
        item {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF47239B), Color(0xFF8E3EFF), Color(0xFFDA72FF)),
                            ),
                        )
                        .padding(20.dp),
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(stringResource(R.string.tarot_random_result), color = Color(0xFFE7D9FF), fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(stringResource(card.nameRes), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color.White.copy(alpha = 0.18f))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                        ) {
                            Text(stringResource(card.keywordRes), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(stringResource(card.meaningRes), color = Color(0xFFF5EEFF), fontSize = 15.sp, lineHeight = 24.sp)
                    }
                }
            }
        }
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF14233C)),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(stringResource(R.string.tarot_love_trend_title), color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(stringResource(loveTrendRes), color = Color(0xFFD8E4EF), fontSize = 15.sp, lineHeight = 24.sp)
                }
            }
        }
        item {
            GradientPrimaryButton(
                text = stringResource(R.string.tarot_try_again),
                enabled = true,
                onClick = onBack,
            )
        }
    }
}

@Composable
fun SynastryDetailScreen(onBack: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { DetailTopBar(title = stringResource(R.string.home_card_synastry_title), onBack = onBack) }
        item {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.linearGradient(listOf(Color(0xFF2046C9), Color(0xFF4F6CFF), Color(0xFF78A4FF))))
                        .padding(20.dp),
                ) {
                    Column {
                        Text(stringResource(R.string.synastry_title), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.synastry_subtitle), color = Color(0xFFE7EEFF), fontSize = 15.sp, lineHeight = 23.sp)
                    }
                }
            }
        }
        item {
            RelationshipMetricCard(
                title = stringResource(R.string.synastry_metric_1_title),
                score = stringResource(R.string.synastry_metric_1_score),
                body = stringResource(R.string.synastry_metric_1_body),
            )
        }
        item {
            RelationshipMetricCard(
                title = stringResource(R.string.synastry_metric_2_title),
                score = stringResource(R.string.synastry_metric_2_score),
                body = stringResource(R.string.synastry_metric_2_body),
            )
        }
        item {
            RelationshipMetricCard(
                title = stringResource(R.string.synastry_metric_3_title),
                score = stringResource(R.string.synastry_metric_3_score),
                body = stringResource(R.string.synastry_metric_3_body),
            )
        }
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF12243B)),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(stringResource(R.string.synastry_advice_title), color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(stringResource(R.string.synastry_advice_body), color = Color(0xFFD8E4EF), fontSize = 15.sp, lineHeight = 24.sp)
                }
            }
        }
    }
}

@Composable
fun SoulmateDetailScreen(onBack: () -> Unit) {
    val profiles = FakeData.matchProfiles
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { DetailTopBar(title = stringResource(R.string.home_card_soulmate_title), onBack = onBack) }
        item {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.linearGradient(listOf(Color(0xFFFF7BC1), Color(0xFFFF90D2), Color(0xFFFFB1C3))))
                        .padding(20.dp),
                ) {
                    Column {
                        Text(stringResource(R.string.soulmate_title), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.soulmate_subtitle), color = Color(0xFFFFF3FA), fontSize = 15.sp, lineHeight = 23.sp)
                    }
                }
            }
        }
        items(profiles.size) { index ->
            SoulmateProfileCard(profile = profiles[index])
        }
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF12243B)),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(stringResource(R.string.soulmate_tips_title), color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(stringResource(R.string.soulmate_tips_body), color = Color(0xFFD8E4EF), fontSize = 15.sp, lineHeight = 24.sp)
                }
            }
        }
    }
}

@Composable
fun HotRoomsDetailScreen(onBack: () -> Unit) {
    val rooms = FakeData.companionRooms.take(3)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { DetailTopBar(title = stringResource(R.string.companion_section_1_title), onBack = onBack) }
        item {
            DetailHeroCard(
                title = stringResource(R.string.hot_rooms_title),
                subtitle = stringResource(R.string.hot_rooms_subtitle),
                colors = listOf(Color(0xFF304B92), Color(0xFF5973D9), Color(0xFF8AA1FF)),
            )
        }
        items(rooms.size) { index ->
            HotRoomCard(room = rooms[index])
        }
        item {
            InfoBlock(
                title = stringResource(R.string.hot_rooms_tips_title),
                body = stringResource(R.string.hot_rooms_tips_body),
            )
        }
    }
}

@Composable
fun DivinationZoneDetailScreen(onBack: () -> Unit) {
    val cards = FakeData.companionCards.filter { it.price != null }.take(3)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { DetailTopBar(title = stringResource(R.string.companion_section_2_title), onBack = onBack) }
        item {
            DetailHeroCard(
                title = stringResource(R.string.divination_zone_title),
                subtitle = stringResource(R.string.divination_zone_subtitle),
                colors = listOf(Color(0xFF5C2AB7), Color(0xFF8752F9), Color(0xFFC889FF)),
            )
        }
        items(cards.size) { index ->
            val card = cards[index]
            InfoBlock(
                title = stringResource(card.titleRes),
                body = stringResource(R.string.divination_zone_item_body, stringResource(card.titleRes), card.price ?: 0),
            )
        }
        item {
            InfoBlock(
                title = stringResource(R.string.divination_zone_tips_title),
                body = stringResource(R.string.divination_zone_tips_body),
            )
        }
    }
}

@Composable
fun HealingNightDetailScreen(onBack: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { DetailTopBar(title = stringResource(R.string.companion_section_3_title), onBack = onBack) }
        item {
            DetailHeroCard(
                title = stringResource(R.string.healing_night_title),
                subtitle = stringResource(R.string.healing_night_subtitle),
                colors = listOf(Color(0xFF163959), Color(0xFF29627A), Color(0xFF49A7A0)),
            )
        }
        item { InfoBlock(title = stringResource(R.string.healing_night_topic_1_title), body = stringResource(R.string.healing_night_topic_1_body)) }
        item { InfoBlock(title = stringResource(R.string.healing_night_topic_2_title), body = stringResource(R.string.healing_night_topic_2_body)) }
        item { InfoBlock(title = stringResource(R.string.healing_night_topic_3_title), body = stringResource(R.string.healing_night_topic_3_body)) }
        item { InfoBlock(title = stringResource(R.string.healing_night_tips_title), body = stringResource(R.string.healing_night_tips_body)) }
    }
}

@Composable
fun ZodiacDetailScreen(onBack: () -> Unit) {
    val profiles = FakeData.zodiacProfiles
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { DetailTopBar(title = stringResource(R.string.banner_title), onBack = onBack) }
        item {
            DetailHeroCard(
                title = stringResource(R.string.zodiac_detail_title),
                subtitle = stringResource(R.string.zodiac_detail_subtitle),
                colors = listOf(Color(0xFF152F92), Color(0xFF304FD6), Color(0xFF77A3FF)),
            )
        }
        items(profiles.size) { index ->
            ZodiacProfileCard(profile = profiles[index])
        }
    }
}

@Composable
private fun RelationshipMetricCard(title: String, score: String, body: String) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF132642)),
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0xFF2B4E95))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                ) {
                    Text(score, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(body, color = Color(0xFFD8E4EF), fontSize = 15.sp, lineHeight = 24.sp)
        }
    }
}

@Composable
private fun DetailHeroCard(title: String, subtitle: String, colors: List<Color>) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(colors))
                .padding(20.dp),
        ) {
            Column {
                Text(title, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(subtitle, color = Color(0xFFF0F6FF), fontSize = 15.sp, lineHeight = 23.sp)
            }
        }
    }
}

@Composable
private fun InfoBlock(title: String, body: String) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF12243B)),
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(title, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(body, color = Color(0xFFD8E4EF), fontSize = 15.sp, lineHeight = 24.sp)
        }
    }
}

@Composable
private fun HotRoomCard(room: CompanionRoom) {
    val context = LocalContext.current
    val tagsText = room.tags.joinToString(" / ") { context.getString(it) }
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = room.accent),
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarBubble(stringResource(room.hostRes), modifier = Modifier.size(44.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(room.titleRes), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(stringResource(R.string.host_name, stringResource(room.hostRes)), color = Color(0xFFE3ECF6), fontSize = 13.sp)
                }
                Text(stringResource(R.string.online_count, room.onlineCount), color = Color(0xFFFFE18A), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                tagsText,
                color = Color(0xFFF5F8FD),
                fontSize = 14.sp,
                lineHeight = 21.sp,
            )
        }
    }
}

@Composable
private fun ZodiacProfileCard(profile: ZodiacProfile) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF132642)),
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0xFF2B4E95))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                ) {
                    Text(stringResource(profile.nameRes), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(stringResource(profile.titleRes), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(stringResource(profile.summaryRes), color = Color(0xFFD8E4EF), fontSize = 15.sp, lineHeight = 24.sp)
        }
    }
}

@Composable
private fun SoulmateProfileCard(profile: MatchProfile) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF132642)),
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarBubble(stringResource(profile.nameRes), modifier = Modifier.size(46.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(profile.nameRes), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(stringResource(profile.signRes), color = Color(0xFF9DB8CF), fontSize = 13.sp)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(profile.accent)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                ) {
                    Text(stringResource(R.string.percent_value, profile.compatibility), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(stringResource(profile.summaryRes), color = Color(0xFFE8EEF5), fontSize = 15.sp, lineHeight = 23.sp)
        }
    }
}

@Composable
private fun DetailTopBar(title: String, onBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White.copy(alpha = 0.08f))
                .clickable { onBack() },
            contentAlignment = Alignment.Center,
        ) {
            Text(stringResource(R.string.back_text), color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}
