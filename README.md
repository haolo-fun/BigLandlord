# BigLandlord
![毕业设计](https://img.shields.io/badge/-%E6%AF%95%E4%B8%9A%E8%AE%BE%E8%AE%A1-blue) ![java版本](https://img.shields.io/badge/java-1.8-brightgreen) ![springboot](https://img.shields.io/badge/spring%20boot-2.4.0-brightgreen) ![mybatis-plus](https://img.shields.io/badge/mybatis--plus-3.5.2-brightgreen)

![mysql](https://img.shields.io/badge/MySQL-8.0.27-yellowgreen) ![redis](https://img.shields.io/badge/Redis-6.2.6-yellowgreen)

## 介绍
这是一个基于spring boot + vue 的房东房屋租赁管理系统

本仓库为项目后端代码，前端代码仓库在[haolo-fun/BigLandlord_web](https://github.com/haolo-fun/BigLandlord_web)

- spring security + JWT
- Mysql + Redis
- [vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)后台模板
- [knif4j](https://github.com/xiaoymin/knife4j)接口文档
- [Element UI](https://element.eleme.io/#/zh-CN)组件
- [阿里云短信服务](https://dysms.console.aliyun.com/overview)
- [支付宝开放平台](https://developers.alipay.com/)

### 实现功能

- [x] 房源信息管理
- [x] 租客信息管理
- [x] 押金管理
- [x] 租单管理
- [x] 财务管理
- [x] 支付系统

## 演示

[演示地址](https://rent.haolo.fun)

[接口文档地址](https://api.rent.haolo.fun/doc.html)

### 部分截图

![首页](https://raw.githubusercontent.com/haolo-fun/ImageHosting/main/%20BigLandlord/202306061311339.png)
![权限](https://raw.githubusercontent.com/haolo-fun/ImageHosting/main/%20BigLandlord/202306061311836.png)
![租单](https://raw.githubusercontent.com/haolo-fun/ImageHosting/main/%20BigLandlord/202306061313345.png)
![附加费](https://raw.githubusercontent.com/haolo-fun/ImageHosting/main/%20BigLandlord/202306061309231.png)
![财务](https://raw.githubusercontent.com/haolo-fun/ImageHosting/main/%20BigLandlord/202306061313183.png)
![支付](https://raw.githubusercontent.com/haolo-fun/ImageHosting/main/%20BigLandlord/202306061314069.png)

## 部署

### 云服务申请

短信服务提供商为阿里云，需要[开通](https://dysms.console.aliyun.com/overview)短信服务，开通后建议在[RAM 访问控制](https://ram.console.aliyun.com/users)创建一个用户，获取AccessKey，再授予`AliyunDysmsFullAccess`权限。

支付使用的是支付宝的沙箱环境，可以[点击这里](https://open.alipay.com/develop/sandbox/app)申请。

**注意：若要使用支付宝，需要有一个公网ip，或者是借助其他工具进行内网穿透。**

### 初始化数据库

将big_landlord.sql导入到数据库中。

### 配置文件

需要修改的文件有两个。

#### /bigLandlord-core/src/main/resources/application-core.yaml

将云服务申请的参数填写至相应配置文件中

```yaml
aliyun:
  sms:
    # 阿里云短信服务
    accessKeyId:
    accessKeySecret:
  pay:
    # 在支付宝创建的应用的id
    app_id:
    # 商户私钥，您的PKCS8格式RSA2私钥
    merchant_private_key:
    # 支付宝公钥
    alipay_public_key:
    # 绑定的商家账号
    sellerId:
    # 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    notify_url: https://《Your IP or Domain》/pay/notify
    # 同步通知，支付成功，一般跳转到成功页
    # return_url:
    # 签名方式
    sign_type: RSA2
    # 字符编码格式
    charset: UTF8
    # 超时时间，可空
    time_out: 30m
    # 支付宝网关: https://openapi.alipaydev.com/gateway.do
    gatewayUrl: https://openapi.alipaydev.com/gateway.do
```

#### /bigLandlord-db/src/main/resources/application-db.yaml

修改数据库相关配置

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/big_landlord?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    username: root
    password: 123456
    hikari:
      max-lifetime: 50000
  redis:
    host: 127.0.0.1
    port: 6379
    password: 123456
    database: 8
    timeout: 5000ms
```
### 打包运行

在root路径下按顺序运行mvn的clean,compile,package即可。

![编译打包](https://raw.githubusercontent.com/haolo-fun/ImageHosting/main/%20BigLandlord/202306061155365.png)

打包完成后，在`/bigLandlord-starter/target/`下找到`bigLandlord-starter-0.0.1-SNAPSHOT.jar`文件。

执行`java -jar bigLandlord-starter-0.0.1-SNAPSHOT.jar`即可。
