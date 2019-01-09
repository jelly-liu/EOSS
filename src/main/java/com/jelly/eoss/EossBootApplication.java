package com.jelly.eoss;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:36 PM 2019/1/9
 * @Description：${description}
 */

@ComponentScan("com.jelly.eoss")
@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class})
@SpringBootApplication
public class EossBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(EossBootApplication.class, args);
    }
}
