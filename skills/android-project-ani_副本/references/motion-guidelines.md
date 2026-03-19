# 动效设计指南

## 基本原则

### 1. 克制使用
动效应该低调、简短、微妙。过度的动画会分散用户注意力，降低效率。

### 2. 服务于可用性
动效的首要目的是提升用户体验，而非装饰。每个动效都应该有明确的功能目的。

### 3. 避免分散注意力
无关的动效会严重降低用户体验。动效应该引导注意力，而非抢夺注意力。

---

## 动效的四大用途

### 1. 反馈确认
确认系统已识别用户操作。

**应用场景：**
- 按钮点击后的视觉反馈
- 表单提交成功/失败
- 操作完成提示

**示例：**
- 按钮按下时轻微缩小
- 点击后出现涟漪效果
- 成功时显示对勾动画

### 2. 状态变化
传达界面切换到不同状态。

**应用场景：**
- 页面切换
- 面板展开/收起
- 模态框打开/关闭
- 加载状态变化

**示例：**
- 页面淡入淡出
- 抽屉滑入滑出
- 弹窗缩放出现

### 3. 空间隐喻
帮助用户理解在信息层级中的位置。

**应用场景：**
- 导航层级变化
- 详情页进入/返回
- 标签页切换

**示例：**
- 子页面从右侧滑入
- 返回时向右滑出
- 层级深入时缩放进入

### 4. 增强可供性
暗示可接受的交互方式。

**应用场景：**
- 可拖拽元素
- 可滑动区域
- 可展开内容

**示例：**
- 轻微晃动提示可拖拽
- 边缘阴影提示可滚动
- 箭头旋转提示可展开

---

## 微交互设计

### 定义
微交互是触发器和反馈组成的配对，是用户与界面之间最小的交互单元。

### 四大核心价值

1. **显示系统状态** - 让用户知道发生了什么
2. **错误预防** - 在问题发生前给予提示
3. **传达品牌** - 通过细节体现品牌个性
4. **鼓励用户参与** - 让交互更有趣味

### 设计要点

- **必须有明确目的** - 每个微交互都要解决具体问题
- **动画应足够微妙** - 不应抢夺用户注意力
- **持续时间要短** - 通常 150-300ms
- **视觉反馈元素放置在触发器附近** - 便于用户感知

---

## 时长规范

### 快速 (100-200ms)
适用于：
- 按钮状态变化
- 悬停效果
- 小元素的出现/消失
- 颜色变化

### 标准 (200-400ms)
适用于：
- 面板展开/收起
- 模态框出现/消失
- 页面元素的进入动画
- 大多数过渡效果

### 缓慢 (400-600ms)
适用于：
- 复杂的页面转场
- 大面积的布局变化
- 需要用户注意的重要变化
- 装饰性动画

### 超过 600ms
一般不推荐，除非：
- 加载动画（循环播放）
- 引导动画
- 特殊的品牌展示

---

## 缓动曲线

### ease-out (推荐用于进入)
```
cubic-bezier(0, 0, 0.2, 1)
```
元素快速出现，然后减速停止。适合元素进入视图。

### ease-in (推荐用于退出)
```
cubic-bezier(0.4, 0, 1, 1)
```
元素缓慢开始，然后加速离开。适合元素离开视图。

### ease-in-out (推荐用于状态变化)
```
cubic-bezier(0.4, 0, 0.2, 1)
```
平滑的开始和结束。适合元素在视图内的状态变化。

### linear
```
cubic-bezier(0, 0, 1, 1)
```
匀速运动。适合进度条、旋转等持续动画。

### 弹性效果
```
cubic-bezier(0.68, -0.55, 0.265, 1.55)
```
带有轻微回弹。适合活泼、有趣的界面。

---

## 常见动效模式

### 淡入淡出 (Fade)
```
进入: opacity 0 → 1, 200-300ms, ease-out
退出: opacity 1 → 0, 150-200ms, ease-in
```

### 缩放 (Scale)
```
进入: scale 0.95 → 1 + opacity 0 → 1, 200ms, ease-out
退出: scale 1 → 0.95 + opacity 1 → 0, 150ms, ease-in
```

### 滑动 (Slide)
```
从右进入: translateX(100%) → 0, 300ms, ease-out
向右退出: translateX(0) → 100%, 250ms, ease-in
从下进入: translateY(20px) → 0 + opacity, 300ms, ease-out
```

### 展开收起 (Expand/Collapse)
```
展开: height 0 → auto, 250ms, ease-out
收起: height auto → 0, 200ms, ease-in
```

### 涟漪效果 (Ripple)
```
从点击位置扩散的圆形
scale 0 → 2, opacity 0.3 → 0, 400ms, ease-out
```

---

## 页面转场

### 前进 (进入子页面)
- 新页面从右侧滑入
- 或新页面淡入 + 轻微放大
- 旧页面可轻微向左移动或淡出

### 后退 (返回上级页面)
- 当前页面向右滑出
- 或当前页面淡出 + 轻微缩小
- 上级页面从左侧滑入或淡入

### 同级切换 (标签页等)
- 淡入淡出
- 或根据方向左右滑动
- 保持切换方向的一致性

---

## 加载动画

### 骨架屏动画
```
背景色脉动:
background-position 动画
从左到右的渐变移动
duration: 1.5s, 循环
```

### 旋转加载
```
rotate 0 → 360deg
duration: 1s, linear, 循环
```

### 点状加载
```
三个点依次跳动
scale 变化 + 位移
duration: 1.2s, 循环
```

### 进度条
```
width 0% → 100%
duration: 根据实际进度
ease-out 或 linear
```

---

## 列表动画

### 列表项进入
```
依次进入，每项延迟 50-100ms
translateY(20px) → 0 + opacity 0 → 1
duration: 300ms, ease-out
```

### 列表项删除
```
height 收缩 + opacity 淡出
duration: 200ms, ease-in
后续项目上移填补空间
```

### 列表项重排
```
position 动画过渡
duration: 300ms, ease-in-out
```

---

## 性能优化

### 优先使用的属性
这些属性可以利用 GPU 加速，性能最佳：
- `transform` (translate, scale, rotate)
- `opacity`

### 避免动画的属性
这些属性会触发重排，性能较差：
- `width`, `height`
- `top`, `left`, `right`, `bottom`
- `margin`, `padding`
- `font-size`

### 优化技巧
1. 使用 `will-change` 提示浏览器
2. 避免同时动画过多元素
3. 在动画期间避免触发重排
4. 使用 `transform` 代替位置属性

---

## 无障碍考虑

### 减少动画偏好
尊重用户的系统设置：
```css
import android.content.Context
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberReducedMotion(): Boolean {
    val context = LocalContext.current
    return remember {
        val scale = Settings.Global.getFloat(
            context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            1f
        )
        scale == 0f
    }
}
```

### 避免的动画类型
- 闪烁效果（可能引发癫痫）
- 大面积的快速移动
- 无法暂停的持续动画
- 自动播放的视频/动画

### 提供控制
- 允许用户暂停/停止动画
- 提供跳过动画的选项
- 确保动画不阻止用户操作
