# Invest_P2P 投资金融（P2P）
p2p金融项目，结合现有第三方应用市场上主流p2p金融理财产品特点，集成了新的技术与框架。<br>
该项目内容包含p2p金融理财业务流程，数据加密、解密，客户端异常信息上传，用户登录注册，数据的图表展示，第三方支付，手势密码，分享功能等。<br>
#### 此项目体现了客户端(Android)、服务器端(Tomcat+j2ee)、数据库的交互.

## 什么是P2P？
P2P金融是指彼此陌生的出资人和借款人，借助网络平台开展借贷的行为。<br>
具体模式就是P2P公司搭建一个网络平台，通过线下寻找借款目标，进行实际情况审核，审核通过后在p2p网贷平台上发布借款需求，平台理财者就可以通过筛选的方式寻找合适的放款目标,把钱借给对方，在一定期限内获得相应
收益，资金需求方取得资金使用权的同时，需要付出一定的资金成本和利息等。
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/%E5%9B%BE%E6%96%87/p2p%E5%B9%B3%E5%8F%B0.jpg)<br>
##### 简单的说就是个人与个人间的借贷交易，一般需要借助电子商务专业网络平台帮助接待双方确立借贷关系并完成相关交易手续。
### 通过此项目可掌握：
1.熟悉金融业务相关知识及业务流程<br>
2.掌握数据加密解密相关操作<br>
3.掌握Activity、Fragment、Adapter等结构代码的抽取<br>
4.掌握支付和提现业务的实现流程<br>
5.熟悉使用更多框架<br>
AsyncHttpClient,ButterKnife,Picasso,ViewPagerIndicator,Banner,MpAndroidChart,GestureLock,shareSDK,加密等。<br>
## 欢迎页面
###### 业务逻辑：
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/%E5%9B%BE%E6%96%87/Welcome.JPG)<br>
##### 效果图：
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/gif/update.gif)<br>
## 主页面
1.联网到服务器(Tomcat)获取图片和数据<br>
2.Banner图片轮播.[GitHub-Banner的使用方法](https://github.com/ljrRookie/banner)<br>
3.自定义圆形进度条<br>
4.自定义ScrollView(在顶端或底部还可以滑动)<br>
##### 效果图：
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/gif/home.gif)<br>
## 投资页面
1.联网到服务器(Tomcat)获取数据<br>
2.自定义TextView（实现跑马灯效果）<br>
3.ListView(adapter的抽取)<br>
4.自定义随机分布布局(第三方：randomLayout 实现:下滑随机分布)<br>
5.自定义流式布局<br>
6.本来需要个tabLayout的，一直报错，个人还无法解决。
##### 效果图：
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/gif/invest.gif)<br>
## 我的页面
###### 登录功能：
(1)进入界面强制弹出登录框<br>
(2)联网到服务器(Tomcat)再向数据库获取用户头像和数据)<br>
(3)登录后在本地保存用户登录信息（SharedPreferences）<br>
(4)下次打开软件判断是否已经登录了<br>
(5)修改用户的头像（跳转图库或相机）<br>
(6)界面显示头像（压缩+裁剪成圆形）
###### 充值（支付宝（还没实现））<br>
###### 提现（还没实现）<br>
###### 图表显示数据(第三方：MpAndroidChart 实现:各种图表)<br>
##### 效果图：
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/gif/MineLogin.gif)
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/gif/Mine.gif)
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/gif/mine2.gif)

## 更多页面
(1)注册功能：注册信息通过服务器添加到数据库中，密码使用了MD5加密<br>
(2)手势密码（GestureLock）<br>
(3)联系客服（点击号码即可打电话）<br>
(4)用户反馈（通过服务器把意见添加到数据库中）<br>
(5)社会化分享（第三方：share）<br>
(6)关于了解
###### 手势密码：
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/%E5%9B%BE%E6%96%87/GestureLock.JPG)<br>
[GitHub-GestureLock](https://github.com/7heaven/GestureLock)<br>
##### 效果图：
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/gif/more1.gif)
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/gif/more2.gif)
![img](https://github.com/ljrRookie/Invest_P2P/blob/master/%E8%B5%84%E6%96%99/gif/more3.gif)


