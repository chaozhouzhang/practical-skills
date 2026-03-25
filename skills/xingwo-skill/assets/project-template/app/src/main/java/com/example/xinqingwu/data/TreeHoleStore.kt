package com.example.xinqingwu.data

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PublishedTreeHolePost(
    val message: String,
    val tags: List<String>,
    val timeLabel: String,
)

object TreeHoleStore {
    private const val prefsName = "tree_hole"
    private const val keyPosts = "posts"
    private const val fieldSeparator = "\u001F"
    private const val recordSeparator = "\u001E"
    private val timestampFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun savePost(context: Context, message: String, tags: List<String>) {
        val cleanMessage = message.trim()
        val cleanTags = tags.map { it.trim() }.filter { it.isNotEmpty() }
        if (cleanMessage.isEmpty()) return

        val allPosts = posts(context).toMutableList()
        allPosts.add(
            0,
            PublishedTreeHolePost(
                message = cleanMessage,
                tags = cleanTags,
                timeLabel = currentTimestamp(),
            ),
        )

        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit()
            .putString(keyPosts, encodePosts(allPosts))
            .apply()
    }

    fun posts(context: Context): List<PublishedTreeHolePost> {
        val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val encoded = prefs.getString(keyPosts, null).orEmpty()
        if (encoded.isBlank()) return emptyList()

        return encoded
            .split(recordSeparator)
            .mapNotNull { record ->
                if (record.isBlank()) return@mapNotNull null
                val parts = record.split(fieldSeparator)
                val message = parts.getOrNull(0)?.trim().orEmpty()
                if (message.isEmpty()) return@mapNotNull null
                val timeLabel = parts.getOrNull(1)?.trim().orEmpty()
                    .ifBlank { currentTimestamp() }
                val tags = parts.getOrNull(2)
                    .orEmpty()
                    .split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                PublishedTreeHolePost(
                    message = message,
                    tags = tags,
                    timeLabel = timeLabel,
                )
            }
    }

    private fun encodePosts(posts: List<PublishedTreeHolePost>): String {
        return posts.joinToString(recordSeparator) { post ->
            listOf(
                post.message.replace(recordSeparator, " ").replace(fieldSeparator, " "),
                post.timeLabel.replace(recordSeparator, " ").replace(fieldSeparator, " "),
                post.tags.joinToString(",").replace(recordSeparator, " ").replace(fieldSeparator, " "),
            ).joinToString(fieldSeparator)
        }
    }

    private fun currentTimestamp(): String = timestampFormatter.format(Date())
}
