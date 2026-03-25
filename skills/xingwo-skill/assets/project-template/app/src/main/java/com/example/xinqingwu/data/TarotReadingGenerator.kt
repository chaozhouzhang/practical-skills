package com.example.xinqingwu.data

import android.content.Context
import com.example.xinqingwu.model.TarotReading
import kotlin.random.Random

object TarotReadingGenerator {
    private data class TarotArchetype(
        val chineseName: String,
        val englishName: String,
        val uprightKeyword: String,
        val reversedKeyword: String,
        val uprightTheme: String,
        val reversedTheme: String,
    )

    private val cards = listOf(
        TarotArchetype("愚者", "The Fool", "新的起點 / 勇敢靠近 / 打開可能", "猶豫試探 / 節奏失焦 / 害怕踏出", "讓關係重新流動", "先把節奏拉回自己"),
        TarotArchetype("魔術師", "The Magician", "主動吸引 / 表達清楚 / 魅力放大", "話說太滿 / 心意分散 / 能量失衡", "把你的吸引力用對地方", "別急著證明自己"),
        TarotArchetype("女祭司", "The High Priestess", "直覺很準 / 先觀察 / 情緒有深度", "悶著不說 / 過度猜測 / 把感受藏起來", "相信你內在早知道的答案", "別讓沉默變成距離"),
        TarotArchetype("皇后", "The Empress", "被疼惜 / 關係滋養 / 互動升溫", "過度付出 / 情緒黏著 / 需求失衡", "讓愛與照顧自然流進來", "先照顧自己再照顧別人"),
        TarotArchetype("皇帝", "The Emperor", "邊界清楚 / 關係穩住 / 給出承諾", "太想掌控 / 不夠柔軟 / 對話卡住", "用穩定感支持關係", "別把保護變成壓力"),
        TarotArchetype("教皇", "The Hierophant", "價值對齊 / 適合認真 / 關係定義", "規則綁太緊 / 被期待壓住 / 不敢做自己", "看見你真正認同的關係模式", "不是所有標準都適合你"),
        TarotArchetype("戀人", "The Lovers", "真心靠近 / 關係選擇 / 雙向回應", "曖昧拉扯 / 選擇困難 / 心口不一", "把喜歡說得更真一點", "不要讓逃避代替答案"),
        TarotArchetype("戰車", "The Chariot", "節奏推進 / 主動出擊 / 關係破冰", "太急太衝 / 期待落差 / 想贏過想懂", "把你想要的關係往前推", "放慢一點才聽得見對方"),
        TarotArchetype("力量", "Strength", "溫柔堅定 / 接住情緒 / 安定陪伴", "壓抑逞強 / 情緒內耗 / 表面沒事", "你有能力穩住眼前的心", "柔軟比硬撐更有力量"),
        TarotArchetype("隱者", "The Hermit", "沉澱整理 / 先聽自己 / 看清需求", "越想越封閉 / 退太遠 / 不願回應", "先把心裡的聲音聽清楚", "別把抽離變成失聯"),
        TarotArchetype("命運之輪", "Wheel of Fortune", "轉機靠近 / 緣分流動 / 意外變化", "反覆繞圈 / 變動失控 / 期待落空", "新的機會正在靠近你", "不必追著每個變化跑"),
        TarotArchetype("正義", "Justice", "看清真相 / 對等互動 / 關係校準", "失衡委屈 / 只算對錯 / 忽略感受", "讓關係回到更公平的位置", "別讓委屈一直累積"),
        TarotArchetype("倒吊人", "The Hanged Man", "換個角度 / 暫停觀察 / 重新理解", "拖延停滯 / 卡住不動 / 不願調整", "這次轉念比衝刺更重要", "你不是沒路，只是還沒換角度"),
        TarotArchetype("死神", "Death", "舊模式結束 / 關係翻頁 / 真正更新", "捨不得放 / 反覆消耗 / 對舊事執著", "讓不適合的節奏停在這裡", "你得先鬆手，新的才進得來"),
        TarotArchetype("節制", "Temperance", "慢慢磨合 / 情緒調和 / 節奏舒服", "忽冷忽熱 / 配速失衡 / 好意被稀釋", "溫和地把彼此調回同一個頻率", "急著求結果只會更亂"),
        TarotArchetype("惡魔", "The Devil", "強烈吸引 / 欲望升高 / 難以抽離", "執著上頭 / 關係綁住 / 情緒被牽動", "看見吸引力背後真正的需求", "別把捨不得當成非他不可"),
        TarotArchetype("高塔", "The Tower", "突然看清 / 假象拆開 / 關係震盪", "混亂未止 / 餘波很重 / 安全感下滑", "有些真相會把你救出來", "先穩住自己，再處理變動"),
        TarotArchetype("星星", "The Star", "慢慢回暖 / 修復期待 / 重新相信", "希望忽遠忽近 / 容易失望 / 等太久", "讓心重新長出一點希望", "別把全部期待綁在一個人身上"),
        TarotArchetype("月亮", "The Moon", "情緒翻湧 / 敏感直覺 / 夢與暗示", "不安放大 / 想太多 / 訊號看不清", "先照顧情緒，再解讀關係", "不是每個沉默都代表拒絕"),
        TarotArchetype("太陽", "The Sun", "明朗互動 / 好感升溫 / 安心靠近", "熱度太快 / 曝曬過頭 / 忽略細節", "把喜歡放到有光的地方", "開心是真的，但別忽略界線"),
        TarotArchetype("審判", "Judgement", "舊人舊事回來 / 內心醒來 / 給出答案", "反覆回頭 / 還沒放下 / 判斷被情緒拉走", "有些答案到了該說清楚的時候", "不要只回顧，要做決定"),
        TarotArchetype("世界", "The World", "圓滿收束 / 關係成熟 / 進入新階段", "差最後一步 / 還沒真正完成 / 留下遺憾", "你正在靠近更完整的關係狀態", "別在最後一步又退回去"),
    )

    fun generate(context: Context): TarotReading {
        val profile = UserProfileStore.getProfile(context)
        val seedSource = listOf(
            profile.nickname,
            profile.birthday,
            profile.gender,
            profile.zodiac,
            profile.chineseZodiac,
            System.currentTimeMillis().toString(),
        ).joinToString("#")
        val random = Random(seedSource.hashCode())
        val card = cards.random(random)
        val isUpright = random.nextBoolean()
        val orientation = if (isUpright) "正位" else "逆位"
        val keyword = if (isUpright) card.uprightKeyword else card.reversedKeyword
        val theme = if (isUpright) card.uprightTheme else card.reversedTheme
        val monthDay = profile.birthday.takeLast(5)
        val genderTone = when (profile.gender) {
            context.getString(com.example.xinqingwu.R.string.profile_gender_female) -> "更細膩地感受關係裡的回應"
            else -> "更直接地面對自己真正的在意"
        }
        val meaning = buildString {
            append(profile.nickname)
            append("，你這次抽到「")
            append(card.chineseName)
            append("」")
            append(orientation)
            append("。")
            append("這張牌會把")
            append(profile.zodiac)
            append("的情緒敏感、")
            append(profile.chineseZodiac)
            append("的本能節奏，和你在 ")
            append(monthDay)
            append(" 這一天出生時帶來的個人氣場一起放大。")
            append("它正在提醒你：")
            append(theme)
            append("，也要記得")
            append(genderTone)
            append("。")
        }
        val loveTrend = buildString {
            append("最近的感情走向會更靠近「")
            append(theme)
            append("」這個主題。")
            append("對")
            append(profile.nickname)
            append("來說，")
            append(profile.zodiac)
            append("的表達方式會影響你怎麼靠近喜歡的人，")
            append(profile.chineseZodiac)
            append("的節奏則決定你願不願意真正打開心。")
            append("這幾天適合先把心意說清楚一點，別只等對方猜。")
        }
        return TarotReading(
            chineseName = card.chineseName,
            englishName = card.englishName,
            orientation = orientation,
            keyword = keyword,
            meaning = meaning,
            loveTrend = loveTrend,
        )
    }
}
