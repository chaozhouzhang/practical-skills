---
name: xingwo-skill
description: 从 0 到 1 生成与当前对话产物一致的 Android 星座陪伴原型 App（Kotlin + Jetpack Compose）。适用于需要快速落地启动页、首页/陪伴/我的三个 tab 主页面、繁体中文文案、假数据、strings.xml 抽离、内置应用 LOGO 资源、Android .gitignore 的场景。优先复制 skill 内置模板工程，再按目标目录、应用名、包名做小范围调整。
---

# XinQingWu Skill

这个 skill 用来直接生成一份接近当前 `xinqingwu` 原型效果的 Android Compose 工程，而不是只输出零散代码。

## 适用场景

- 用户要从 0 开始生成一个 Android 原型项目
- 风格接近本次对话中的星座 / 陪伴 / 测算产品
- 技术栈明确为 Kotlin + Jetpack Compose
- 需要内置繁体中文假数据与 `strings.xml`
- 需要主流程：启动页后直接进入主页面
- 主页面仅保留 `首頁 / 陪伴 / 我的` 三个 tab，不要讯息页
- 需要使用模板内置的应用 LOGO 资源
- 需要所有跨页面跳转都通过新 Activity 完成

## 默认做法

1. 优先使用 `scripts/new_xingwo_project.sh` 进行交互式创建。
2. 如果上下文里已经给了目标目录、应用名、包名，也可以直接使用 `scripts/init_from_template.sh`。
3. 复制完成后，按需做少量定制，不要重写整套工程。
4. 保持以下约束不变，除非用户明确要求修改：
   - 纯本地假数据，无网络请求
   - Compose 绘制主要视觉，不依赖远程图片
   - 所有用户可见文案进入 `app/src/main/res/values/strings.xml`
   - 文案使用繁體中文，语气偏台湾产品
   - 根目录包含 Android 常用 `.gitignore`
   - 底部 tab 只有 `首頁 / 陪伴 / 我的`
   - 应用图标使用模板内置的 PNG LOGO 资源，不使用系统默认图标
   - 所有跨页面导航都使用新 Activity，不在当前 Activity 里靠弹窗或隐藏显示切页
   - 主页面 `首頁 / 陪伴 / 我的` 三个 tab 内的主要显示元素都应可点击，并跳转到新的详情页面；详情内容要根据点击前的 UI 元素做差异化展开，不可全部复用同一份静态说明
   - 不必把所有详情页都塞进同一个 Activity；对于信息结构明显不同的入口，应拆成独立详情 Activity
  - 当前模板中至少要把首页的 `今日運勢` 与 `十二星座`、`十二生肖人格地圖`、`塔羅牌解讀` 做成独立详情页
  - 当前模板中至少要把陪伴页的四个核心入口做成独立详情页：`心靈樹洞`、`塔羅測算`、`星盤合盤`、`靈魂伴侶`
   - `今日運勢` 详情页复用首页卡片核心内容；`心靈樹洞` 展示 5-10 条匿名心情发言；`塔羅測算` 需根据用户昵称、生日、星座、生肖、性别动态生成塔罗结果，不可只保留固定 5 张牌；牌池至少应覆盖足够丰富的塔罗类型，并在详情页展示中文名、英文名、牌位、关键词和最近感情运势走向
   - `心靈樹洞` 详情页顶部需增加“發布心情”按钮，进入独立发布页；发布页至少支持填写心情内容与选择心情标签；详情页示例区需改为“你可以這樣說”，示例名字统一显示为当前用户昵称
   - `星盤合盤` 详情页应展开成关系维度分析页，至少包含多项默契/沟通/长期磨合指标；`靈魂伴侶` 详情页应展示多位高契合对象与相处建议
   - 陪伴页的 `熱門房間`、`占卜測算`、`療癒夜話` 三个分区入口也应拆成独立详情页，分别对应房间集合、测算入口说明、晚间疗癒陪伴内容
  - `十二星座` Banner 不应继续走通用详情页，应拆成专门的星座人格介绍页，集中展示 12 星座的人格特质介绍；首页中其下方还应补充 `十二生肖人格地圖` 入口，并进入独立生肖人格介绍页；在 `十二生肖` 下方还应补充 `塔羅牌解讀` 入口，并进入独立的塔羅牌百科详情页，覆盖大阿爾克那与小阿爾克那说明
   - 按钮、图标、图片等点击热区不能过小，默认在视觉元素外侧额外扩出约 `10dp`
   - 首页移除今日運勢上方旧头部 UI 后，首个内容区仍需和状态栏保留明显顶部间距
   - 陪伴页移除 Banner 上方全部二级 tab
   - 主页面触发系统返回时，要先弹出退出确认，用户确认后直接退出应用
   - `我的` tab 中移除 `我的訂單`，并在 `設定中心` 上方增加 `隱私政策` 入口；点击后必须进入独立隐私政策页面
   - `我的` tab 需包含独立的个人资料页面，至少支持昵称、性别、生日的显示与修改，并根据生日自动生成星座、生肖；若用户未设置，则默认昵称为 `心晴屋`、性别为 `男`、生日为首次打开应用当天
   - 首页 `今日運勢` 不可写死，需根据用户设置的昵称、性别、生日、星座、生肖以及当前日期，本地动态生成当天的分数、分项柱状值与建议文案
   - 隐私政策页面必须使用 Android `TextView` 加载本地文案，文案应满足 Google Play 上架时常见的隐私披露结构

## 初始化命令

先执行：

```bash
bash /Users/zhangchaozhou/.agents/skills/xingwo-skill/scripts/new_xingwo_project.sh
```

这个脚本会依次询问：

- 目标目录
- 应用名
- 包名

如果你已经知道参数，也可以直接执行：

```bash
bash /Users/zhangchaozhou/.agents/skills/xingwo-skill/scripts/init_from_template.sh <target_dir> [app_name] [package_name]
```

示例：

```bash
bash /Users/zhangchaozhou/.agents/skills/xingwo-skill/scripts/init_from_template.sh \
  /Users/zhangchaozhou/Documents/Github/my-xinqingwu \
  "心晴屋" \
  "com.example.xinqingwu"
```

## 模板工程内容

模板位于 `assets/project-template/`，已经包含：

- Gradle Kotlin DSL 工程
- 多 Activity 入口与页面流转
- `Splash / Main` 与多种详情 / 基础 Activity
- `首頁 / 陪伴 / 我的` 三 tab
- 首页顶部精简版布局与状态栏安全间距
- 主页面返回退出确认弹窗
- 首页、陪伴页、我的页的假数据内容
- 统一 `strings.xml`
- `我的` tab 的个人资料页与本地资料持久化
- 内置 PNG 应用 LOGO 与 Manifest 图标替换
- `.gitignore`

## 工作流

### 1. 收集生成参数

优先顺序如下：

1. 用户明确给出的目标目录 / 应用名 / 包名
2. 交互脚本 `scripts/new_xingwo_project.sh`
3. 若只缺应用名或包名，可使用模板默认值

如果缺少目标目录，不要擅自猜测一个陌生路径；要么使用交互脚本，要么直接向用户确认。

### 2. 复制模板

执行初始化脚本，把模板复制到目标目录。

### 3. 必要替换

如果用户提供了自定义信息，仅改这些位置：

- 工程显示名：`settings.gradle.kts`
- `applicationId` 与 `namespace`
- Java/Kotlin 包路径与 `package` 声明
- `strings.xml` 中 `app_name`

不要大面积重构模板。

### 4. 校验关键要求

至少检查：

- `app/build.gradle.kts` 使用 Compose
- `strings.xml` 存在且承载全部用户可见文案
- `FakeData.kt` 只引用字符串资源 ID，不写中文文案常量
- `BottomTab` 只有三个 tab
- `AndroidManifest.xml` 不得使用系统默认 `sym_def_app_icon`
- 导航型交互不得继续使用底部弹窗模拟新页面
- Splash 启动时必须直接进入主页面，不得再插入登录或登录态判断
- 主页面里的卡片、chip、房间、任务、统计、记录等主要展示元素都应跳到新的详情 Activity，且详情页文案需按来源元素定制
- 对于结构差异大的详情页，优先拆成专用 Activity，而不是继续堆到通用详情模板中
  - 至少校验首页的 `今日運勢`、`十二星座`、`十二生肖人格地圖`、`塔羅牌解讀` 已接到独立详情 Activity
- 至少校验陪伴页的 `熱門房間`、`占卜測算`、`療癒夜話` 已接到独立详情 Activity
 - 至少校验陪伴页的 `心靈樹洞`、`塔羅測算`、`星盤合盤`、`靈魂伴侶` 已接到独立详情 Activity，且首页的 `十二星座` Banner 已接到独立的星座人格详情 Activity，并补充独立的 `十二生肖人格地圖` 与 `塔羅牌解讀` 入口及详情 Activity
- 首页今日運勢卡片上方不应再保留旧头部 UI，但要保留与状态栏的顶部间距
- 陪伴页 Banner 上方不应再保留二级 tab
- 主页面返回时必须先弹出退出确认，确认后直接退出应用
- `我的` tab 中不得再出现 `我的訂單`，`隱私政策` 入口必须位于 `設定中心` 上方并能跳到独立页面
- 隐私政策页面不得继续沿用 Compose 文档页，必须改为 `TextView` 加载本地隐私政策正文
- 根目录 `.gitignore` 已忽略 `.gradle`、`.kotlin`、`.idea`、`build`、`app/build`、`local.properties`

### 5. 若用户要继续迭代

优先在现有结构中增量修改，不要推翻模板。默认保持以下目录结构：

```text
app/src/main/java/<package>
├── MainActivity.kt
├── data/FakeData.kt
├── model/Models.kt
└── ui
    ├── XinQingWuApp.kt
    ├── components/CommonComponents.kt
    ├── screens/AuthScreens.kt
    ├── screens/MainScreens.kt
    └── theme/Theme.kt
```

## 参考资料

- 对话需求摘要：`references/spec.md`
- 交互创建脚本：`scripts/new_xingwo_project.sh`
- 模板复制脚本：`scripts/init_from_template.sh`

只有在你需要确认约束时，再读取 `references/spec.md`。
