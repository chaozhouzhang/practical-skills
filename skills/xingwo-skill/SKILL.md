---
name: xingwo-skill
description: 从 0 到 1 生成与当前对话产物一致的 Android 星座陪伴原型 App（Kotlin + Jetpack Compose）。适用于需要快速落地启动页、精简后的快速登入页、手机号登录页、独立 Activity 页面流、首页/陪伴/我的三 tab、繁体中文文案、假数据、strings.xml 抽离、自动应用 LOGO、Android .gitignore 的场景。优先复制 skill 内置模板工程，再按目标目录、应用名、包名做小范围调整。
---

# XingWo Skill

这个 skill 用来直接生成一份接近当前 `xingwo` 原型效果的 Android Compose 工程，而不是只输出零散代码。

## 适用场景

- 用户要从 0 开始生成一个 Android 原型项目
- 风格接近本次对话中的星座 / 陪伴 / 测算产品
- 技术栈明确为 Kotlin + Jetpack Compose
- 需要内置繁体中文假数据与 `strings.xml`
- 需要主流程：启动页、登录入口页、手机号登录页、登录后主页面
- 登录后仅保留 `首頁 / 陪伴 / 我的` 三个 tab，不要讯息页
- 需要自动生成并替换应用 LOGO
- 需要快速登入页只保留手机号登录入口与协议勾选区域
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
   - 应用图标使用模板自带的自动生成 LOGO，不使用系统默认图标
   - 快速登入页移除 Google、Facebook、Line、返回按钮
   - 快速登入页必须有协议勾选逻辑；未勾选时点击手机号登录要提示
   - 快速登入页协议勾选默认是已勾选状态
   - 用戶服務條款與隱私權政策必须可点击，并跳转到对应页面
   - 手机登录页手机号输入框默认值必须为空
   - 手机登录页登入 / 註冊按钮只要手机号与密码都非空即可高亮并允许点击
   - 手机登录页不提供忘記密碼入口
   - 登录成功后必须持久化记住已登录状态；只要用户没有主动登出，重启应用或杀进程后再次打开都应直接进入登录后主页面
   - 所有跨页面导航都使用新 Activity，不在当前 Activity 里靠弹窗或隐藏显示切页
   - 登录后 `首頁 / 陪伴 / 我的` 三个 tab 内的主要显示元素都应可点击，并跳转到新的详情页面；详情内容要根据点击前的 UI 元素做差异化展开，不可全部复用同一份静态说明
   - 按钮、图标、图片等点击热区不能过小，默认在视觉元素外侧额外扩出约 `10dp`
   - 首页移除今日運勢上方旧头部 UI 后，首个内容区仍需和状态栏保留明显顶部间距
   - 陪伴页移除 Banner 上方全部二级 tab
   - 登录后的主页面触发返回时，要先弹出登出确认，再返回快速登入页

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
  /Users/zhangchaozhou/Documents/Github/my-xingwo \
  "星窩 Prototype" \
  "com.example.xingwo"
```

## 模板工程内容

模板位于 `assets/project-template/`，已经包含：

- Gradle Kotlin DSL 工程
- 多 Activity 入口与页面流转
- `Splash / AuthLanding / PhoneLogin / CountryPicker / Terms / Privacy / Main`
- 用戶服務條款 / 隱私權政策页面
- `首頁 / 陪伴 / 我的` 三 tab
- 首页顶部精简版布局与状态栏安全间距
- 主页面返回登出确认弹窗
- 首页、陪伴页、我的页的假数据内容
- 统一 `strings.xml`
- 自绘应用 LOGO 与 Manifest 图标替换
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
- 快速登入页不得出现 Google / Facebook / Line / 返回按钮
- 协议勾选、错误提示、条款跳转与隐私跳转已接通
- 快速登入页协议勾选默认应为已勾选
- 手机登录页手机号默认值为空字符串
- 手机登录按钮启用条件只检查“手机号非空 + 密码非空”
- 手机登录页不得出现忘記密碼入口
- 条款页、隐私页、国家选择页、手机号页、主页面都通过 Activity 跳转进入
- 导航型交互不得继续使用底部弹窗模拟新页面
- 登录成功后必须写入本地登录态；Splash 启动时应根据登录态决定进入快速登入页还是主页面；任何登出动作都必须清除登录态
- 登录后主页面里的卡片、chip、房间、任务、统计、记录等主要展示元素都应跳到新的详情 Activity，且详情页文案需按来源元素定制
- 首页今日運勢卡片上方不应再保留旧头部 UI，但要保留与状态栏的顶部间距
- 陪伴页 Banner 上方不应再保留二级 tab
- 登录后的主页面返回时必须先弹出登出确认
- 根目录 `.gitignore` 已忽略 `.gradle`、`.kotlin`、`.idea`、`build`、`app/build`、`local.properties`

### 5. 若用户要继续迭代

优先在现有结构中增量修改，不要推翻模板。默认保持以下目录结构：

```text
app/src/main/java/<package>
├── MainActivity.kt
├── data/FakeData.kt
├── model/Models.kt
└── ui
    ├── XingWoApp.kt
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
