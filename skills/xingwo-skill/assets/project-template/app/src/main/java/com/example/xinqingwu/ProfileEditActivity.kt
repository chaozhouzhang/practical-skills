package com.example.xinqingwu

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xinqingwu.data.UserProfileStore
import com.example.xinqingwu.ui.XinQingWuPageContainer
import com.example.xinqingwu.ui.theme.XinQingWuTheme

class ProfileEditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XinQingWuTheme {
                XinQingWuPageContainer {
                    ProfileEditScreen(onBack = ::finish)
                }
            }
        }
    }
}

@Composable
private fun ProfileEditScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val profile = remember { UserProfileStore.getProfile(context) }
    var nickname by remember { mutableStateOf(profile.nickname) }
    var gender by remember { mutableStateOf(profile.gender) }
    var birthday by remember { mutableStateOf(profile.birthday) }
    val birthdayProfile = remember(nickname, gender, birthday) {
        UserProfileStore.getProfilePreview(context, nickname, gender, birthday)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF081325))
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.profile_edit_back),
                color = Color(0xFF67F1E4),
                fontSize = 15.sp,
                modifier = Modifier.clickable { onBack() },
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.profile_edit_title),
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
                    text = stringResource(R.string.profile_edit_hint),
                    color = Color(0xFFAAC0D3),
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.profile_field_nickname),
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    OutlinedTextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = profileTextFieldColors(),
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = stringResource(R.string.profile_field_gender),
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        GenderChoiceChip(
                            text = stringResource(R.string.profile_gender_male),
                            selected = gender == stringResource(R.string.profile_gender_male),
                            onClick = { gender = context.getString(R.string.profile_gender_male) },
                        )
                        GenderChoiceChip(
                            text = stringResource(R.string.profile_gender_female),
                            selected = gender == stringResource(R.string.profile_gender_female),
                            onClick = { gender = context.getString(R.string.profile_gender_female) },
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.profile_field_birthday),
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val calendar = UserProfileStore.birthdayCalendar(birthday)
                                DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        birthday = UserProfileStore.formatBirthday(year, month, dayOfMonth)
                                    },
                                    calendar.get(java.util.Calendar.YEAR),
                                    calendar.get(java.util.Calendar.MONTH),
                                    calendar.get(java.util.Calendar.DAY_OF_MONTH),
                                ).show()
                            },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C3152)),
                    ) {
                        Text(
                            text = birthday,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp),
                        )
                    }
                }

                ProfileReadonlyField(
                    label = stringResource(R.string.profile_field_zodiac),
                    value = birthdayProfile.zodiac,
                    hint = context.getString(R.string.profile_auto_generated_hint, birthdayProfile.zodiac),
                )

                ProfileReadonlyField(
                    label = stringResource(R.string.profile_field_chinese_zodiac),
                    value = birthdayProfile.chineseZodiac,
                    hint = context.getString(R.string.profile_auto_generated_hint, birthdayProfile.chineseZodiac),
                )

                Button(
                    onClick = {
                        UserProfileStore.saveProfile(context, nickname, gender, birthday)
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF67F1E4)),
                ) {
                    Text(
                        text = stringResource(R.string.profile_edit_save),
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
private fun ProfileReadonlyField(label: String, value: String, hint: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1C3152)),
        ) {
            Text(
                text = value,
                color = Color(0xFF96AFC3),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp),
            )
        }
        Text(
            text = hint,
            color = Color(0xFF7E96AC),
            fontSize = 13.sp,
            lineHeight = 20.sp,
        )
    }
}

@Composable
private fun GenderChoiceChip(text: String, selected: Boolean, onClick: () -> Unit) {
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
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
        )
    }
}

@Composable
private fun profileTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color(0xFF67F1E4),
    unfocusedBorderColor = Color(0xFF35506E),
    cursorColor = Color(0xFF67F1E4),
    focusedContainerColor = Color(0xFF1C3152),
    unfocusedContainerColor = Color(0xFF1C3152),
)
