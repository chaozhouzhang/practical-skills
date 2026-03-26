package com.example.xinqingwu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.xinqingwu.R
import com.example.xinqingwu.model.DetailPageContent
import com.example.xinqingwu.ui.components.GradientPrimaryButton

@Composable
fun DetailScreen(
    content: DetailPageContent,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White.copy(alpha = 0.08f))
                .clickable { onBack() }
                .padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.back_text),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF173A72), Color(0xFF5B2FA3), Color(0xFF1A7FA5)),
                        ),
                    )
                    .padding(22.dp),
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color.White.copy(alpha = 0.14f))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                    ) {
                        Text(content.category, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(content.title, color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black, lineHeight = 36.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(content.subtitle, color = Color(0xFFE6EDF7), fontSize = 15.sp, lineHeight = 23.sp)
                    if (content.highlights.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(18.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            content.highlights.take(2).forEach { item ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(999.dp))
                                        .background(Color.White.copy(alpha = 0.16f))
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                ) {
                                    Text(item, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = stringResource(R.string.detail_page_hint),
            color = Color(0xFF98AEC2),
            fontSize = 13.sp,
            lineHeight = 20.sp,
        )
        Spacer(modifier = Modifier.height(18.dp))
        content.sections.forEach { section ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF12243B)),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(section.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(section.body, color = Color(0xFFD8E3ED), fontSize = 15.sp, lineHeight = 24.sp)
                }
            }
        }
        if (content.actionText.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            GradientPrimaryButton(
                text = content.actionText,
                enabled = true,
                onClick = onBack,
            )
        }
    }
}
