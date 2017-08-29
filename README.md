# pay-master
支付宝，微信，

项目注册发现依赖 eureka

cloud-grpc-client 注解实现 可与springboot集成
cloud-grpc-client-netfix 配置文件 可与spring集成

启动顺序：
    
    cloud-eureka-server
    cloud-grpc-client
    cloud-grpc-server

启动方式: mvn spring-boot:run 

访问地址: http://localhost:8080