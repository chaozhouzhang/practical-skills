# XingWoPrototype

基於設計稿生成的 Android 原型工程，使用 Kotlin + Jetpack Compose。

## 頁面流

1. 啟動頁 `Splash`
2. 登入入口頁 `AuthLanding`
3. 手機號登入頁 `PhoneLogin`
4. 主頁面 `Main`
   - 首頁
   - 陪伴
   - 我的

## 專案結構

```text
XingWoPrototype
├── app
│   ├── build.gradle.kts
│   └── src/main
│       ├── AndroidManifest.xml
│       ├── java/com/example/xingwo
│       │   ├── MainActivity.kt
│       │   ├── data/FakeData.kt
│       │   ├── model/Models.kt
│       │   └── ui
│       │       ├── XingWoApp.kt
│       │       ├── components/CommonComponents.kt
│       │       ├── screens/AuthScreens.kt
│       │       ├── screens/MainScreens.kt
│       │       └── theme/Theme.kt
│       └── res
│           ├── values
│           └── xml
├── build.gradle.kts
├── gradle
│   ├── libs.versions.toml
│   └── wrapper
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

## 說明

- 當前全部為假資料，無網路請求，無後端依賴。
- Logo 與主要視覺元素使用 Compose 繪製，沒有引入額外圖片資源。
- 底部導覽只保留 `首頁 / 陪伴 / 我的`，未實作訊息頁。
