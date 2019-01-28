package com.jelly.eoss.configuration;

import com.jelly.eoss.util.PropertyUtil;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 12:32 AM 2019/1/11
 * @Description：${description}
 *
 */

@Configuration
@AutoConfigureAfter(MyBatisConfiguration.class)//注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
public class MyBatisMapperScannerConfiguration {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() throws Exception {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(PropertyUtil.getProperty("mybatis.mapper-scan"));
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }
}
