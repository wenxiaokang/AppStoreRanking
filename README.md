### 开发环境
- 开发工具：AndroidStudio
- Gradle Plugin Version:3.6.3(向下兼容)
- Gradle Version:6.1.1
- Jdk :1.8
### 项目运行
##### 1.调试
Run--->Run'app'
##### 2.打包debug.apk
Build--->Build Bundle(s)/Apk(s)--->Build Apk(s)

apk目录：app/build/outputs/apk/debug/app-debug.apk

### 单元测试
##### 类ExampleInstrumemtedText
##### 方法
- testGetRecommendApi() 调用接口，获取10条数据
- testGetRecommendApiMuiltThread() 多线程并发调用获取app列表的接口，每次调用获取10条数据
- testGetRankingApi() 调用本地数据并存储数据


### 项目相关
#### 1.整体框架
##### MVP:BaseMvpActivity、BaseView、BasePresenter
Presenter层处理数据相关操作，通过View回调给Activity进行页面处理、逻辑跳转。



#### 2.网络请求
##### Retrofit+RxJava+Gson
通过Rxjava获取网络数据，gson转化成java对象，回调给UI主线程

#### 3.屏幕适配
根据设计图纸的尺寸设置宽高
AndroidManifest
```
<manifest>
    <application>    
        ...
        <meta-data
            android:name="design_width_in_dp"
            android:value="360"/>
        <meta-data
            android:name="design_height_in_dp"
            android:value="640"/>      
        ...
     </application>           
```
如需自定义尺寸，如横竖屏切换时，Activity 实现 CustomAdapt

#### 4.自定义控件
###### RoudImagaView.java
圆角图片

###### SlideRecycler.java
处理竖向滑动列表切换横向滑动列表的滑动冲突
#### 5.数据存储
使用SQLite，封装DBHelp数据库操作类。

app数据刷新时，将首页的的为我推荐以及排行榜前10条数据进行存储，当第二次进入app时，将app的本地数据先显示，获取网络数据后刷新



