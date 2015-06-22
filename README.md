# 分布式温控系统

### 邮电大学软件工程大作业 (2012211313班 C组)


---

### 1. 工程说明

* 开发语言: Java, GUI 使用 Swing
* 运行环境: JDK 1.5 以上
* 集成开发环境(IDE): Intellij IDEA 14.0.2
* 数据库版本: MySQL 5.6

### 2. 工程源代码

源代码位于 `app/` 文件夹中
* `app/server`: 主机
* `app/client`: 丛控机

### 3. 数据库建库脚本

脚本位于 `app/server/sql/` 文件夹中

* `access_log.sql`:  丛机登录登出信息
* `cost_report.sql`:  报表
* `transaction_log.sql`:  丛机事物请求交互记录
* `user.sql`:  主机管理员信息

### 4. 资源文件
资源文件在 `app/client/src/main/resources` 文件夹中, 内容为丛控机的输出功率 以及工作模式的切图

### 5. Setup
#### a) 主机
配置 `app\server\src\main\java\cn\bupt\airsys.Configure.java` 
```java
    /** 端口配置 */
    public static final int DEFAULT_PORT = ;
    public static final int DEFAULT_SEND_PORT = ;
    /** 丛控机端口配置 */
    public static final int SLAVE_PORT = ;

    /** JDBC configure */
    public static final String DBMS = "mysql";
    public static final String DB_HOST = "";
    public static final String DB_DATABASE = "";
    public static final String DB_DSN = "jdbc:" + DBMS + "://" + DB_HOST + "/" + DB_DATABASE;
    public static final String DB_USER = "";
    public static final String DB_PASSWD = "";

    /** 丛控机消息推送频率配置 */
    public static final int HEARTBEAT_TICK = 1000 * 5; // 5 sec
    /** 最大负载数量控制 */
    public static final int MAX_SERV_NUM = 3;
    
    /** 单位功率花费配置 */
    public static final float PRICE[] = {0.0f, 0.8f * 5 * 0.0001f, 1.0f * 5 * 0.0001f, 1.3f * 5 * 0.0001f};
```
    
#### b) 丛机
配置 `app\client\src\main\java\cn\bupt\airsys\client\Configure.java`

```java
    /** 端口号配置 */
    public static final int DEFAULT_PORT = 8989;
    public static final int DEFAULT_RECV_PORT = 12345;
    /** 主机端口号 */
    public static final int REMOTE_PORT = 8888;
    /** 主机IP 地址 */
    public static final String REMOTE_IP = "";

    /** 房间号码 */
    public static String ROOM_ID = "3";

    /** tick time of sending data(current status) to master */
    public static final int DEFAULT_TICK = 1000 * 5; // 5 sec
```

### Maven 构建运行
** 加入libs 文件夹中的 *.jar **