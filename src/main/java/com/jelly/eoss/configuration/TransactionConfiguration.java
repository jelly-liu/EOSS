package com.jelly.eoss.configuration;

import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 11:23 AM 2019/1/9
 * @Description：${description}
 */

@Configuration
public class TransactionConfiguration {
    @Autowired
    DataSource dataSource;

    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionInterceptor advice(){
        /*
        //this type of create TransactionInterceptor was also work
        Properties transactionAttributes = new Properties();
        transactionAttributes.put("tx*", "PROPAGATION_REQUIRED");
        transactionAttributes.put("newTx*", "PROPAGATION_REQUIRES_NEW");
        transactionAttributes.put("*", "PROPAGATION_REQUIRED,readOnly");

        TransactionInterceptor txAdvice = new TransactionInterceptor();
        txAdvice.setTransactionManager(txManager());
        txAdvice.setTransactionAttributes(transactionAttributes);
        */

        DefaultTransactionAttribute REQUIRED = new DefaultTransactionAttribute();
        REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        DefaultTransactionAttribute REQUIRED_NEW = new DefaultTransactionAttribute();
        REQUIRED_NEW.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        DefaultTransactionAttribute REQUIRED_READ_ONLY = new DefaultTransactionAttribute();
        REQUIRED_READ_ONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        REQUIRED_READ_ONLY.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("tx*", REQUIRED);
        source.addTransactionalMethod("txNew*", REQUIRED_NEW);
        source.addTransactionalMethod("*", REQUIRED_READ_ONLY);

        return new TransactionInterceptor(transactionManager(), source);
    }

//    @Bean
//    public Pointcut pointcut(){
//        String expression = "";
//        expression += "execution(* com.jelly.eoss.service..*.*(..)) ";
//        expression += "or ";
//        expression += "execution(* com.jelly.eoss.web.admin..*.*(..)) ";
//
//        AspectJExpressionPointcut txPointcut = new AspectJExpressionPointcut();
//        txPointcut.setExpression(expression);
//        return txPointcut;
//    }

    @Bean
    public Advisor advisor(){
//        DefaultPointcutAdvisor txAdvisor = new DefaultPointcutAdvisor(pointcut(), transactionInterceptor());
//        return txAdvisor;

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(advice());
        advisor.setExpression("execution(* com.jelly.eoss.service..*.*(..)) or execution(* com.jelly.eoss.web.admin..*.*(..))");
        return advisor;
    }
}
