package com.open.eoss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class,
//                DataSourceTransactionManagerAutoConfiguration.class,
                RedisAutoConfiguration.class
        }
)
@MapperScan({"com.open.*.db.mapper"})//mybatis mapper scan
@EnableTransactionManagement
public class EossEeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EossEeApplication.class, args);
    }
}
