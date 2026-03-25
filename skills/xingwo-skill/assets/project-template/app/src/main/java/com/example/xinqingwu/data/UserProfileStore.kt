package com.example.xinqingwu.data

import android.content.Context
import com.example.xinqingwu.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class UserProfile(
    val nickname: String,
    val gender: String,
    val birthday: String,
)

object UserProfileStore {
    private const val prefsName = "user_profile"
    private const val keyNickname = "nickname"
    private const val keyGender = "gender"
    private const val keyBirthday = "birthday"
    private const val birthdayPattern = "yyyy/MM/dd"

    fun getProfile(context: Context): UserProfile {
        val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val defaultNickname = context.getString(R.string.profile_default_name)
        val defaultGender = context.getString(R.string.profile_gender_male)
        val defaultBirthday = todayString()

        val storedNickname = prefs.getString(keyNickname, null).orEmpty().trim()
        val storedGender = prefs.getString(keyGender, null).orEmpty().trim()
        val storedBirthday = prefs.getString(keyBirthday, null).orEmpty().trim()

        val nickname = storedNickname.ifBlank { defaultNickname }
        val gender = storedGender.ifBlank { defaultGender }
        val birthday = storedBirthday.ifBlank { defaultBirthday }

        if (storedNickname != nickname || storedGender != gender || storedBirthday != birthday) {
            prefs.edit()
                .putString(keyNickname, nickname)
                .putString(keyGender, gender)
                .putString(keyBirthday, birthday)
                .apply()
        }

        return UserProfile(
            nickname = nickname,
            gender = gender,
            birthday = birthday,
        )
    }

    fun saveProfile(context: Context, nickname: String, gender: String, birthday: String) {
        val profile = UserProfile(
            nickname = nickname.trim().ifBlank { context.getString(R.string.profile_default_name) },
            gender = gender.trim().ifBlank { context.getString(R.string.profile_gender_male) },
            birthday = birthday.trim().ifBlank { todayString() },
        )
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit()
            .putString(keyNickname, profile.nickname)
            .putString(keyGender, profile.gender)
            .putString(keyBirthday, profile.birthday)
            .apply()
    }

    fun formatBirthday(year: Int, month: Int, dayOfMonth: Int): String {
        return String.format(Locale.getDefault(), "%04d/%02d/%02d", year, month + 1, dayOfMonth)
    }

    fun birthdayCalendar(birthday: String): Calendar {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat(birthdayPattern, Locale.getDefault())
        formatter.isLenient = false
        try {
            val parsedDate = formatter.parse(birthday)
            if (parsedDate != null) {
                calendar.time = parsedDate
            }
        } catch (_: ParseException) {
            calendar.timeInMillis = System.currentTimeMillis()
        }
        return calendar
    }

    private fun todayString(): String {
        val formatter = SimpleDateFormat(birthdayPattern, Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}
