#项目构建
####1、在git上拉取代码 git clone http://gitlab.itapl.com/yangsq/ys-cloud.git
####2、项目拉取下来之后引入父工程：ys-cloud，然后所有其他服务的Module都会引入
####3、本地的maven镜像最好换成国内的镜像，例如阿里，网易的，初始化项目的时候会下很多依赖包，比较耗时


#项目启动
###1、首先在项目中的nacos文件夹下把nacos解压到本地，由于nacos不支持8.x以上版本的数据库，我们自己的数据库是8.x以上版本的，所以我这边修改了nacos的数据库驱动重新打了个包，windows就用.zip的
###2、解压压缩包，然后在conf目录下的application.properties文件的末尾添加数据库配置，如下
####spring.datasource.platform=mysql
####db.num=1
####db.url.0=jdbc:mysql://47.98.63.50:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
####db.user=nacos
####db.password=Apl@nacos123 
###3、配置修改好之后，在bin文件夹里面双击startup.cmd 启动nacos
###4、然后本地通过http://locahost:8848/nacos进入nacos的页面，用户名密码均为nacos，登录进入管理页面之后点击左侧菜单，配置管理下面的配置列表查看刚才脚本导入的配置是否成功
![binaryTree](/pic/1590548895.jpg "binaryTree")
然后可以在此管理页面修改配置信息，比如可以修改数据库连接地址
![binaryTree](/pic/1590548996.jpg "binaryTree")
###5、nacos安装启动配置成功，并且本地项目maven构建完毕之后，逐一运行每个服务下的xxxApplication启动项目
###6、所有服务启动成功后，可以在postman里面测试对应接口，由于我们所有服务统一走网关鉴权，所以需要先通过用户名密码获取到token，然后携带token再去访问其他服务
#####1）首先需要在请求头定义好client端的密钥，密钥其实就是ys:auth的base64的加密，key是Authorization，value是Basic eXM6MTIzNDU2
![binaryTree](/pic/1590549146.jpg "binaryTree")
#####2）在请求获取token接口的时候，需要通过/auth/captcha?key=123先获取到验证码
![binaryTree](/pic/1590571764.jpg "binaryTree")
#####3）然后如下图获取token
![binaryTree](/pic/1590571753.jpg "binaryTree")
###7 token获取成功后，访问其他资源服务器的接口需要将token放置头信息
![binaryTree](/pic/1590550933.jpg "binaryTree")


#项目部署（初版）
##后期考虑使用持续化集成部署方式，所以此版本为初版
###1、首先在本地需要把项目打包
###2、打包成功后，将对应的包放在服务器/home/ys里面对应的目录下
![binaryTree](/pic/1590551677.jpg "binaryTree")
###3、每个对应的服务目录都有一个DockerFile的文件，需要先将jar包生成为docker镜像，执行目录下的run.sh即可
![binaryTree](/pic/1590551834.jpg "binaryTree")
###4、然后使用命令docker images查看镜像是否生成成功，接着顺序执行所有服务目录里的run.sh生成各个服务的docker镜像
![binaryTree](/pic/1590571358.jpg "binaryTree")
###5、所有镜像生成后，进入到ys-cloud目录里面执行命令docker-compoes up -d 命令将刚才生成好的镜像挂在的宿主机上
![binaryTree](/pic/1590571437.jpg "binaryTree")
###6、之后通过命令docker ps 查看正在运行的容器，查看是否挂载成功
![binaryTree](/pic/1590571252.jpg "binaryTree")
###7、查看日志，在ys-cloud下面有各个容器日志查看的shell脚本，通过执行不同的日志脚本，查看不同容器的日志
![binaryTree](/pic/1590571501.jpg "binaryTree")
####例如看ys-auth的日志，如下图
![binaryTree](/pic/1590571623.jpg "binaryTree")





