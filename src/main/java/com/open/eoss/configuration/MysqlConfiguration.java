package com.open.eoss.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 9:40 AM 2019/1/10
 * @Description：
 * MySQL, Redis, RabbitMQ, ETC...
 */

@Configuration
public class MysqlConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

//    @Bean
//    public JdbcTemplate jdbcTemplate(){
//        return new JdbcTemplate(dataSource());
//    }
//
//    @Bean
//    public TransactionManager transactionManager(){
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Bean
//    public TransactionInterceptor advice(){
//        DefaultTransactionAttribute REQUIRED = new DefaultTransactionAttribute();
//        REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//
//        DefaultTransactionAttribute REQUIRED_NEW = new DefaultTransactionAttribute();
//        REQUIRED_NEW.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//
//        DefaultTransactionAttribute REQUIRED_READ_ONLY = new DefaultTransactionAttribute();
//        REQUIRED_READ_ONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        REQUIRED_READ_ONLY.setReadOnly(true);
//
//        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
//        source.addTransactionalMethod("tx*", REQUIRED);
//        source.addTransactionalMethod("txNew*", REQUIRED_NEW);
//        source.addTransactionalMethod("*", REQUIRED_READ_ONLY);
//
//        return new TransactionInterceptor(transactionManager(), source);
//    }
//
//    @Bean
//    public Advisor advisor(){
//        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
//        advisor.setAdvice(advice());
////        advisor.setExpression("execution(* com.jelly.eoss.service..*.*(..)) or execution(* com.jelly.eoss.web.admin..*.*(..))");
//        advisor.setExpression("execution(* com.open.eoss.service..*.*(..))");
//        return advisor;
//    }
}
