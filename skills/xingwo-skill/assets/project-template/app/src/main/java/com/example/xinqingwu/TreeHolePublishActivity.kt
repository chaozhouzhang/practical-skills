package com.example.xinqingwu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xinqingwu.data.TreeHoleStore
import com.example.xinqingwu.ui.XinQingWuPageContainer
import com.example.xinqingwu.ui.theme.XinQingWuTheme

class TreeHolePublishActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
                    TreeHolePublishScreen(onBack = ::finish)
                }
            }
        }
    }
}

@Composable
private fun TreeHolePublishScreen(onBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var message by remember { mutableStateOf("") }
    var customTags by remember { mutableStateOf("") }
    val selectedTags = remember { mutableStateListOf<String>() }
    val availableTags = listOf(
        stringResource(R.string.tree_hole_publish_tag_healing),
        stringResource(R.string.tree_hole_publish_tag_relationship),
        stringResource(R.string.tree_hole_publish_tag_work),
        stringResource(R.string.tree_hole_publish_tag_sleep),
        stringResource(R.string.tree_hole_publish_tag_growth),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF081325))
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.profile_edit_back),
                color = Color(0xFF67F1E4),
                fontSize = 15.sp,
                modifier = Modifier.clickable { onBack() },
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.tree_hole_publish_title),
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF13233B)),
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.tree_hole_publish_hint),
                    color = Color(0xFFB5C7D8),
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.tree_hole_publish_field_message),
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = treeHoleTextFieldColors(),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.tree_hole_publish_placeholder),
                                color = Color(0xFF7E96AC),
                            )
                        },
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = stringResource(R.string.tree_hole_publish_field_tags),
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        availableTags.forEach { tag ->
                            PublishTagChip(
                                text = tag,
                                selected = selectedTags.contains(tag),
                                onClick = {
                                    if (selectedTags.contains(tag)) {
                                        selectedTags.remove(tag)
                                    } else {
                                        selectedTags.add(tag)
                                    }
                                },
                            )
                        }
                    }
                    OutlinedTextField(
                        value = customTags,
                        onValueChange = { customTags = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = treeHoleTextFieldColors(),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.tree_hole_publish_custom_tags_placeholder),
                                color = Color(0xFF7E96AC),
                            )
                        },
                    )
                }

                Button(
                    onClick = {
                        val manualTags = customTags
                            .split(" ", "，", ",", "#", "\n", "\t")
                            .map { it.trim() }
                            .filter { it.isNotEmpty() }
                        TreeHoleStore.savePost(context, message, (selectedTags + manualTags).distinct())
                        onBack()
                    },
                    enabled = message.trim().isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF67F1E4),
                        disabledContainerColor = Color(0xFF35506E),
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.tree_hole_publish_action),
                        color = Color(0xFF071725),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun PublishTagChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(999.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF67F1E4) else Color(0xFF1C3152),
        ),
    ) {
        Text(
            text = text,
            color = if (selected) Color(0xFF071725) else Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        )
    }
}

@Composable
private fun treeHoleTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color(0xFF67F1E4),
    unfocusedBorderColor = Color(0xFF35506E),
    cursorColor = Color(0xFF67F1E4),
    focusedContainerColor = Color(0xFF1C3152),
    unfocusedContainerColor = Color(0xFF1C3152),
)
