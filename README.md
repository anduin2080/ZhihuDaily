仿知乎日报
-------------------
## 一、引导页面

### XML文件
先画好UI，一个ImageView和一个TextView。

### Activity 逻辑
Activity的主体逻辑，是从服务器获取资源，设置给布局上的控件。

### 获取网络数据
GuideProtocol 是从网络/缓存获取json数据的一个封装类
1. 先从本地获取json数据
2. 如果为空，再从服务器上获取，并且保存到本地缓存
3. 若不为空，则直接解析json数据
   
## 二、主页面
主页面从上到下由DrawerLayout + ToolBar + SwipeRefreshLayout + RecylerView + HomeProtocol组成。

+ DrawerLayout : 用于实现抽屉效果的侧边栏菜单。
+ ToolBar : 用于实现标题栏，类似以前ActionBar。
+ SwipeRefreshLayout：用于实现下拉刷新效果，直接包裹着RecylerView。
+ RecylerView：主要用于显示日报的列表的ListView。

### DrawerLayout 主页面和侧边栏抽屉效果
DrawerLayout是androidx包提供的一个widget，需要在activity和布局文件里边操作来实现这个效果。布局界面包含侧边栏界面和主页面界面。

### ToolBar
ToolBar是android5.0推出的一个新控件，用来替换ActionBar。

### SwipeRefreshLayout下拉
刷新
下拉刷新只需要在布局文件的最外层包裹他就可以了，布局文件请看activity_content.xml的布局文件。

### RecylerView列表控件
1. 设置布局管理器。
2. 设置适配器。
3. 设置Item相关ItemAnimator，增删动画。

在ContentActivity里面数据加载后，设置RecylerView。

### HomeProtocol加载数据
HomeProtocol的逻辑实际上和GuideProtocol逻辑是一样的，那么此刻可以提取出一个基类，然后子类去实现各自的方法便可以。
基类，提取为BaseProtocol，后面子类只需要各自实现三个抽象方法便可以。
1. paserJsonData(String json) …//解析json数据
2. getCacheDir()….//获取缓存的文件路劲
3. getUrl()…//获取网页地址

### ViewPager主页面第一条Item
用不同的viewHolder，设置不同的type，让adapter区分处理。详见HomeRecylerViewAdapter，其他Item用CardView来实现。

## 主页面上拉刷新
### 上拉逻辑实现
首先，上拉的过程中，需要到最后一个条目才会去加载。因此我们需要判断此时RecylerView可见的最后一个条目位置，是不是刚好等于总的条目数。

### ToolBar Title的实时更新
针对这个问题，无非就是在Recylerview滑动的过程中，实时更新它。问题的关键其实是怎么获取到这个Item所对应的日期。我们在HomeStoriesInfo这个类中，添加两个成员，
1. 一个是存储当天条目的所对应的日期
2. 一个是当前条目是否是显示日期标志位。

### 上拉刷新数据的获取和保存
之前主页面和引导页面分别用HomeProtocol和GuideProtocol来加载，这次同样生成一个OldNewsProtocol用来加载以前的数据。并且这次数据并不包含HomeTopInfo相关，只有HomeStoriesInfo对应的json数据。

## 主页面条目显示

ViewPager的点击事件响应，本来一开始打算用setOnPageChangeListener来实现，但发现这个回调已经在实现轮询播放的时候占用了并且每次滑动都会调用，所以不应该在这里做处理。那么只能在viewPager的adapter里面加载页面的时候做响应。RecylerClickListener是一个接口，用来响应条目的点击事件，在ContentActivity实现这个接口。

在HomeRecylerViewAdapter的MyViewHolder里面实现点击，调用这个接口。必须是设置Holder.itemView的click监听才能实现点击回调。

ContentActivity实现RecylerClickListener接口。

NewsInfoActivity就是显示日报具体某条新闻的页面，组成就是一条顶端的bar+webView。

CommentActivity是显示评论的界面，这里顺手也写出来，同样是顶端bar+RecylerView来实现。

