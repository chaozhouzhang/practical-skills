package com.example.xingwo.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xingwo.R
import com.example.xingwo.data.FakeData
import com.example.xingwo.model.TarotCard
import com.example.xingwo.ui.components.AvatarBubble
import com.example.xingwo.ui.components.GradientPrimaryButton

@Composable
fun FortuneDetailScreen(onBack: () -> Unit) {
    val fortune = FakeData.dailyFortune
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
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF314A4D)),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(stringResource(R.string.section_today_fortune), color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(stringResource(R.string.icon_trend_up), color = Color(0xFFBED2D7), fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(R.string.number_value, fortune.score), color = Color(0xFFFFE082), fontSize = 58.sp, fontWeight = FontWeight.Black)
                            Text(stringResource(fortune.suggestionRes), color = Color(0xFFE6F0F1), fontSize = 15.sp, lineHeight = 22.sp)
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
                                            .clip(RoundedCornerShape(999.dp))
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
    }
}

@Composable
fun TreeHoleDetailScreen(onBack: () -> Unit) {
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
                }
            }
        }
        items(FakeData.treeHolePosts.size) { index ->
            val post = FakeData.treeHolePosts[index]
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF12243B)),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AvatarBubble(stringResource(post.nameRes).take(1), modifier = Modifier.size(44.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stringResource(post.nameRes), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(stringResource(post.timeRes), color = Color(0xFF8FA7BC), fontSize = 12.sp)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color(0xFF304F71))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                        ) {
                            Text(stringResource(post.moodRes), color = Color(0xFF9DE7FF), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(stringResource(post.messageRes), color = Color(0xFFE4EDF6), fontSize = 15.sp, lineHeight = 23.sp)
                }
            }
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
