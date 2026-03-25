package com.example.xinqingwu.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.xinqingwu.R
import com.example.xinqingwu.model.DailyFortune
import com.example.xinqingwu.model.FortuneBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.absoluteValue

object DailyFortuneGenerator {
    fun generate(context: Context, profile: UserProfile = UserProfileStore.getProfile(context)): DailyFortune {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)
        val seed = listOf(
            profile.nickname,
            profile.gender,
            profile.birthday,
            profile.zodiac,
            profile.chineseZodiac,
            today,
        ).joinToString("|").hashCode().absoluteValue

        val love = score(seed, 0, 68, 98)
        val career = score(seed, 1, 66, 97)
        val wealth = score(seed, 2, 64, 96)
        val social = score(seed, 3, 65, 95)
        val study = score(seed, 4, 63, 94)
        val overall = (love + career + wealth + social + study) / 5

        val primary = listOf(
            context.getString(R.string.fortune_love) to love,
            context.getString(R.string.fortune_career) to career,
            context.getString(R.string.fortune_wealth) to wealth,
            context.getString(R.string.fortune_social) to social,
            context.getString(R.string.fortune_study) to study,
        ).maxByOrNull { it.second }?.first ?: context.getString(R.string.fortune_love)

        val action = actions(context)[seed % actions(context).size]
        val tone = tones(context)[(seed / 7) % tones(context).size]
        val reminder = reminders(context)[(seed / 13) % reminders(context).size]

        return DailyFortune(
            score = overall,
            suggestion = context.getString(
                R.string.fortune_suggestion_format,
                profile.nickname,
                profile.zodiac,
                profile.chineseZodiac,
                primary,
                action,
                tone,
                reminder,
            ),
            bars = listOf(
                FortuneBar(R.string.fortune_love, love, Color(0xFFFF78C1)),
                FortuneBar(R.string.fortune_career, career, Color(0xFF9B86FF)),
                FortuneBar(R.string.fortune_wealth, wealth, Color(0xFF68B9FF)),
                FortuneBar(R.string.fortune_social, social, Color(0xFFFFC857)),
                FortuneBar(R.string.fortune_study, study, Color(0xFFE778FF)),
            ),
        )
    }

    private fun score(seed: Int, offset: Int, min: Int, max: Int): Int {
        val range = max - min + 1
        val mixed = (seed / (offset + 3) + offset * 97).absoluteValue
        return min + (mixed % range)
    }

    private fun actions(context: Context) = listOf(
        context.getString(R.string.fortune_action_relationship),
        context.getString(R.string.fortune_action_career),
        context.getString(R.string.fortune_action_wealth),
        context.getString(R.string.fortune_action_social),
        context.getString(R.string.fortune_action_study),
    )

    private fun tones(context: Context) = listOf(
        context.getString(R.string.fortune_tone_gentle),
        context.getString(R.string.fortune_tone_confident),
        context.getString(R.string.fortune_tone_steady),
        context.getString(R.string.fortune_tone_open),
    )

    private fun reminders(context: Context) = listOf(
        context.getString(R.string.fortune_reminder_rest),
        context.getString(R.string.fortune_reminder_focus),
        context.getString(R.string.fortune_reminder_timing),
        context.getString(R.string.fortune_reminder_expression),
    )
}
