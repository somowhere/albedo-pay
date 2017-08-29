# pay-master
支付宝，微信，grpc 通用支付平台

##项目依赖 
[albedo-boot](https://github.com/somewhereMrli/albedo-boot)
[albedo-grpc](https://github.com/somewhereMrli/albedo-grpc)


启动顺序：
    
    pay-eureka-server
    pay-rpc-server
    pay-web

启动方式: mvn spring-boot:run 

访问地址: http://localhost:8080