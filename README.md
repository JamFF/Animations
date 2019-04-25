# Animations
Android Animations

## 手写动画框架

1. MyObjectAnimator
    * 负责动画的创建、启动、执行，真正处理都是交给MyFloatPropertyValuesHolder；
    * 设置动画的时长
    * 设置动画的插值器

2. MyFloatPropertyValuesHolder
    * 动画真正的处理类。
    * 将动画随时间变化的一组值（values）交给 MyKeyframeSet 转换为关键帧保存起来。
    * 修改 View 的属性，完成动画。

3. MyKeyframeSet
    关键帧集合。
    * 计算每个关键帧的位置百分比。
    * 估值器，计算下一帧的值。

4. MyFloatKeyframe
    关键帧，变量保存着某一时刻的具体状态。
    * mFraction：当前进度的百分比。
    * mValue：当前值。

    例如设置 `float... values` 为 `1f, 1.5f, 2f, 3f`，那么 mValue 分别为`1f, 1.5f, 2f, 3f`，mFraction 分别为`0、0.25、0.5、1`。

    
5. VSYNCManager
    VSync 信号，屏幕刷新频率是 60Hz，也就是每秒刷新 60次（每秒 60帧），也就是每隔 16ms 刷新一次。引入VSync 信号，相比每个动画设置定时器有三个优点：
    * 由系统统一触发完成绘制。
    * 每个动画绘制完成是公平的，不会因为当前帧没有完成绘制，出现阻塞的现象。举个例子，某个绘制花费时间是 24ms，在16ms时得到 VSync 信号后，无法进行正常渲染，这样就发生了丢帧现象。那么用户在 32ms内看到的会是同一帧画面。
    * 节省资源，由系统提供一个，如果每个动画都有自己的定时器，非常耗性能。

    由于第三方应用是不能监听 VSync 信号，这里通过线程睡眠 16ms 模拟 VSync 信号。

6. LinearInterpolator
    线性插值器，通过传入百分比，返回新的百分比，达到加速、减速的效果。

7. FloatEvaluator
    估值器，计算下一帧的值。

## 视差动画

1. 自定义容器，ParallaxContainer
2. 自定义LayoutInflater，ParallaxLayoutInflater
3. 原生控件添加自定义属性
4. 自定义动画

参考：

[Android -- 仿小红书欢迎界面](https://blog.csdn.net/weixin_34381687/article/details/85865968)  
[XhsParallaxWelcome](https://github.com/w446108264/XhsParallaxWelcome)  
[ParallaxPager](https://github.com/prolificinteractive/ParallaxPager)