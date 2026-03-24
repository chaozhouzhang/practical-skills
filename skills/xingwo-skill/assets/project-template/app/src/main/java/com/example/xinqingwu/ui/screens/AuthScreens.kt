package com.example.xinqingwu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.xinqingwu.R
import com.example.xinqingwu.data.FakeData
import com.example.xinqingwu.model.CountryCode
import com.example.xinqingwu.ui.components.GradientPrimaryButton
import com.example.xinqingwu.ui.components.PlanetLogo

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PlanetLogo(modifier = Modifier.size(120.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Text(stringResource(R.string.brand_name), color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(220.dp))
            Text(stringResource(R.string.brand_slogan), color = Color(0xFFD8DFEA), fontSize = 18.sp, letterSpacing = 3.sp)
        }
    }
}

@Composable
fun AuthLandingScreen(
    onPhoneLogin: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    var agreed by rememberSaveable { mutableStateOf(true) }
    var showAgreementError by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 18.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            PlanetLogo()
            Spacer(modifier = Modifier.height(12.dp))
            Text(stringResource(R.string.brand_name), color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(stringResource(R.string.brand_slogan), color = Color(0xFFB8CCDC), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(90.dp))
            Text(stringResource(R.string.quick_login), color = Color(0xFF9BB4C9), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(26.dp))
            WhiteActionButton(stringResource(R.string.login_phone)) {
                if (agreed) {
                    showAgreementError = false
                    onPhoneLogin()
                } else {
                    showAgreementError = true
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            AgreementSection(
                agreed = agreed,
                showError = showAgreementError,
                onCheckedChange = {
                    agreed = !agreed
                    if (agreed) showAgreementError = false
                },
                onTermsClick = onTermsClick,
                onPrivacyClick = onPrivacyClick,
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun AgreementSection(
    agreed: Boolean,
    showError: Boolean,
    onCheckedChange: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onCheckedChange() }
                    .background(
                        if (agreed) Color(0xFF4DF2E2) else Color.Transparent,
                        RoundedCornerShape(7.dp),
                    )
                    .border(
                        width = 1.5.dp,
                        color = if (agreed) Color(0xFF4DF2E2) else Color(0xFF73A9B5),
                        shape = RoundedCornerShape(7.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                if (agreed) {
                    Text(
                        text = stringResource(R.string.symbol_check),
                        color = Color(0xFF041A25),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                    )
                } else {
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            AgreementText(
                onTermsClick = onTermsClick,
                onPrivacyClick = onPrivacyClick,
                modifier = Modifier.weight(1f),
            )
        }
        if (showError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.agreement_required),
                color = Color(0xFFFF9B9B),
                fontSize = 13.sp,
            )
        }
    }
}

@Composable
fun PhoneLoginScreen(
    country: CountryCode,
    onBack: () -> Unit,
    onOpenCountryPicker: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp, vertical = 20.dp),
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopStart)
                .clickable { onBack() },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.symbol_close),
                color = Color.White,
                fontSize = 28.sp,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            PlanetLogo(modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.height(42.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2B38)),
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 44.dp)
                            .clickable { onOpenCountryPicker() },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.country_picker_value, stringResource(country.localNameRes)),
                            color = Color(0xFFA9A7AF),
                            fontSize = 16.sp,
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(stringResource(country.codeRes), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(stringResource(R.string.symbol_separator), color = Color(0xFF656A76))
                        Spacer(modifier = Modifier.width(10.dp))
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it.filter(Char::isDigit).take(11) },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text(stringResource(R.string.hint_phone)) },
                            colors = phoneFieldColors(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it.take(16) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(stringResource(R.string.hint_password)) },
                        colors = phoneFieldColors(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    )
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
            GradientPrimaryButton(
                text = stringResource(R.string.login_register),
                enabled = phone.isNotBlank() && password.isNotBlank(),
                onClick = onLoginSuccess,
            )
        }
    }
}

@Composable
private fun WhiteActionButton(text: String, onClick: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF4F8FB),
            contentColor = Color(0xFF141D32),
        ),
    ) {
        Text(text, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun AgreementText(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val normalColor = Color(0xFFB8CCDC)
    val linkColor = Color(0xFF4DF2E2)
    val annotatedText = buildAnnotatedString {
        append(stringResource(R.string.agree_prefix))
        withStyle(SpanStyle(color = linkColor, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = "terms", annotation = "terms")
            append(stringResource(R.string.terms_title))
            pop()
        }
        append(stringResource(R.string.agree_connector))
        withStyle(SpanStyle(color = linkColor, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = "privacy", annotation = "privacy")
            append(stringResource(R.string.privacy_title))
            pop()
        }
    }

    androidx.compose.foundation.text.ClickableText(
        text = annotatedText,
        modifier = modifier,
        style = androidx.compose.ui.text.TextStyle(
            color = normalColor,
            fontSize = 15.sp,
            lineHeight = 22.sp,
        ),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "terms", start = offset, end = offset)
                .firstOrNull()
                ?.let { onTermsClick() }
            annotatedText.getStringAnnotations(tag = "privacy", start = offset, end = offset)
                .firstOrNull()
                ?.let { onPrivacyClick() }
        },
    )
}

@Composable
fun LegalDocumentScreen(
    titleRes: Int,
    contentRes: Int,
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Text(
                    text = stringResource(R.string.back_text),
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(titleRes),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(18.dp))
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF14253B)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp),
                ) {
                    Text(
                        text = stringResource(contentRes),
                        color = Color(0xFFD9E6F2),
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@Composable
fun CountryPickerScreen(
    selectedIndex: Int,
    onBack: () -> Unit,
    onSelectCountry: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Box(
            modifier = Modifier
                .clickable { onBack() }
                .padding(horizontal = 10.dp, vertical = 10.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = stringResource(R.string.back_text),
                color = Color.White,
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.select_country_region),
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(FakeData.countries) { index, item ->
                    CountryRow(
                        country = item,
                        onClick = { onSelectCountry(index) },
                    )
                    if (index == selectedIndex) {
                        Text(
                            text = stringResource(R.string.selected_country_hint),
                            color = Color(0xFF1F9E93),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 24.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CountryRow(country: CountryCode, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 44.dp)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(
                R.string.country_row_value,
                stringResource(country.localNameRes),
                stringResource(country.englishNameRes),
            ),
            color = Color(0xFF272C36),
            fontSize = 18.sp,
        )
        Text(stringResource(country.codeRes), color = Color(0xFF8D939A), fontSize = 18.sp)
    }
}

@Composable
private fun phoneFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    cursorColor = Color.White,
    focusedBorderColor = Color.Transparent,
    unfocusedBorderColor = Color.Transparent,
    focusedPlaceholderColor = Color(0xFF6B7280),
    unfocusedPlaceholderColor = Color(0xFF6B7280),
)
