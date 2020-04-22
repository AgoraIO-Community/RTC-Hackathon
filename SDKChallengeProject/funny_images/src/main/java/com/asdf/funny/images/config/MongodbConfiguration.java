package com.asdf.funny.images.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongodbConfiguration {
    @Value("${spring.data.mongodb.host}")
    private String MONGODB_HOST;
    @Value("${spring.data.mongodb.port}")
    private int MONGODB_PORT;
    @Value("${spring.data.mongodb.name}")
    private String MONGODB_NAME;
    @Value("${spring.data.mongodb.password}")
    private String MONGODB_PASSWORD;
//    @Value("${defualt.agentOpen}")
//    private String agentOpen;//1为开启代理
    @Bean
    // @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public MongoClient MongoClientConnectionFactory() {
//       if(agentOpen.equals("1")){
        System.out.println("======......MongoDBUtil初始化(有认证)......======" + this.MONGODB_HOST + ":" + this.MONGODB_PORT);
        new MongoClient(this.MONGODB_HOST, this.MONGODB_PORT);
        Builder options = new Builder();
        options.cursorFinalizerEnabled(true);
        options.connectionsPerHost(300);
        options.connectTimeout(30000);
        options.maxWaitTime(5000);
        options.socketTimeout(0);
        options.threadsAllowedToBlockForConnectionMultiplier(5000);
        List<ServerAddress> seeds = new ArrayList();
        ServerAddress address1 = new ServerAddress(this.MONGODB_HOST, this.MONGODB_PORT);
        seeds.add(address1);
        MongoCredential credential = MongoCredential.createScramSha1Credential(this.MONGODB_NAME, "admin", this.MONGODB_PASSWORD.toCharArray());
        List<MongoCredential> credentials = new ArrayList();
        credentials.add(credential);
        options.writeConcern(WriteConcern.SAFE);
        MongoClient mongoClient = new MongoClient(seeds, credentials, options.build());
        return mongoClient;
//
//       }else{
//
//
//           System.out.println("======......MongoDBUtil初始化(无认证)......======" + MONGODB_HOST + ":" + MONGODB_PORT);
//           // String ip ="10.206.20.247";
//           // int port =28000;
//           MongoClient mongoClient = new MongoClient(MONGODB_HOST, MONGODB_PORT);
//           // 大部分用户使用mongodb都在安全内网下，但如果将mongodb设为安全验证模式，就需要在客户端提供用户名和密码：
//           // boolean auth = db.authenticate(myUserName, myPassword);
//           Builder options = new Builder();
//           options.cursorFinalizerEnabled(true);
//           // options.autoConnectRetry(true);// 自动重连true
//           // options.maxAutoConnectRetryTime(10); // the maximum auto connect
//           // retry time
//           options.connectionsPerHost(300);// 连接池设置为300个连接,默认为100
//           options.connectTimeout(30000);// 连接超时，推荐>3000毫秒
//           options.maxWaitTime(5000); //
//           options.socketTimeout(0);// 套接字超时时间，0无限制
//           options.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out
//           // of
//           // semaphores
//           // to get
//           // db”错误。
//           options.writeConcern(WriteConcern.SAFE);//
//           options.build();
//
//           return mongoClient;
//           }
//       }
    }
    }
