package com.example.xinqingwu.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlanetLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(124.dp)) {
            drawCircle(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFB1F5E8), Color(0xFFFFC874), Color(0xFFFF7E65)),
                ),
            )
            drawLine(
                color = Color(0xFFFFFFFF),
                start = Offset(18f, 28f),
                end = Offset(size.width - 16f, 40f),
                strokeWidth = 11f,
                cap = StrokeCap.Round,
            )
            drawLine(
                color = Color(0xFF233A63),
                start = Offset(12f, 56f),
                end = Offset(size.width - 14f, 56f),
                strokeWidth = 10f,
                cap = StrokeCap.Round,
            )
            drawLine(
                color = Color(0xFFFFD3A1),
                start = Offset(14f, 76f),
                end = Offset(size.width - 18f, 86f),
                strokeWidth = 9f,
                cap = StrokeCap.Round,
            )
        }
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .offset(
                        x = listOf((-48).dp, 46.dp, (-56).dp, 56.dp)[index],
                        y = listOf((-38).dp, (-34).dp, 26.dp, 18.dp)[index],
                    )
                    .size(8.dp)
                    .background(
                        color = listOf(
                            Color(0xFFFFA657),
                            Color(0xFF6EF2E5),
                            Color(0xFF6EF2E5),
                            Color(0xFFFFA657),
                        )[index],
                        shape = RoundedCornerShape(2.dp),
                    ),
            )
        }
    }
}

@Composable
fun GradientPrimaryButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = if (enabled) {
        listOf(Color(0xFFFFB45D), Color(0xFF60E9E1))
    } else {
        listOf(Color(0xFF7A5E46), Color(0xFF3A6667))
    }
    androidx.compose.material3.Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.horizontalGradient(colors), shape = RoundedCornerShape(28.dp))
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text, color = Color(0xFF10162A), fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun SectionTitle(title: String, action: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        if (action != null) {
            Text(text = action, color = Color(0xFFA5BCD4), fontSize = 13.sp)
        }
    }
}

@Composable
fun AvatarBubble(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(0xFFFDAB75)),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = name.takeLast(1),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
    }
}
