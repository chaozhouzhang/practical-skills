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
    val zodiac: String,
    val chineseZodiac: String,
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

        return buildProfile(context, nickname, gender, birthday)
    }

    fun getProfilePreview(context: Context, nickname: String, gender: String, birthday: String): UserProfile {
        return buildProfile(
            context = context,
            nickname = nickname.trim().ifBlank { context.getString(R.string.profile_default_name) },
            gender = gender.trim().ifBlank { context.getString(R.string.profile_gender_male) },
            birthday = birthday.trim().ifBlank { todayString() },
        )
    }

    fun saveProfile(context: Context, nickname: String, gender: String, birthday: String) {
        val profile = getProfilePreview(context, nickname, gender, birthday)
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

    private fun buildProfile(context: Context, nickname: String, gender: String, birthday: String): UserProfile {
        val birthdayCalendar = birthdayCalendar(birthday)
        return UserProfile(
            nickname = nickname,
            gender = gender,
            birthday = birthday,
            zodiac = zodiacName(context, birthdayCalendar),
            chineseZodiac = chineseZodiacName(context, birthdayCalendar.get(Calendar.YEAR)),
        )
    }

    private fun zodiacName(context: Context, calendar: Calendar): String {
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val zodiacRes = when {
            (month == 3 && day >= 21) || (month == 4 && day <= 19) -> R.string.zodiac_aries_name
            (month == 4 && day >= 20) || (month == 5 && day <= 20) -> R.string.zodiac_taurus_name
            (month == 5 && day >= 21) || (month == 6 && day <= 21) -> R.string.zodiac_gemini_name
            (month == 6 && day >= 22) || (month == 7 && day <= 22) -> R.string.zodiac_cancer_name
            (month == 7 && day >= 23) || (month == 8 && day <= 22) -> R.string.zodiac_leo_name
            (month == 8 && day >= 23) || (month == 9 && day <= 22) -> R.string.zodiac_virgo_name
            (month == 9 && day >= 23) || (month == 10 && day <= 23) -> R.string.zodiac_libra_name
            (month == 10 && day >= 24) || (month == 11 && day <= 22) -> R.string.zodiac_scorpio_name
            (month == 11 && day >= 23) || (month == 12 && day <= 21) -> R.string.zodiac_sagittarius_name
            (month == 12 && day >= 22) || (month == 1 && day <= 19) -> R.string.zodiac_capricorn_name
            (month == 1 && day >= 20) || (month == 2 && day <= 18) -> R.string.zodiac_aquarius_name
            else -> R.string.zodiac_pisces_name
        }
        return context.getString(zodiacRes)
    }

    private fun chineseZodiacName(context: Context, year: Int): String {
        val zodiacCycle = listOf(
            R.string.chinese_zodiac_rat,
            R.string.chinese_zodiac_ox,
            R.string.chinese_zodiac_tiger,
            R.string.chinese_zodiac_rabbit,
            R.string.chinese_zodiac_dragon,
            R.string.chinese_zodiac_snake,
            R.string.chinese_zodiac_horse,
            R.string.chinese_zodiac_goat,
            R.string.chinese_zodiac_monkey,
            R.string.chinese_zodiac_rooster,
            R.string.chinese_zodiac_dog,
            R.string.chinese_zodiac_pig,
        )
        val index = Math.floorMod(year - 2020, zodiacCycle.size)
        return context.getString(zodiacCycle[index])
    }
}
