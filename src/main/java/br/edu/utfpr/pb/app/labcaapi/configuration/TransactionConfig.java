package br.edu.utfpr.pb.app.labcaapi.configuration;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Bean
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager txManager) {
        RuleBasedTransactionAttribute txAttr = new RuleBasedTransactionAttribute();
        txAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttr.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class))
        );

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("*", txAttr);
        return new TransactionInterceptor(txManager, source);
    }

    @Bean
    public Advisor transactionAdvisor(PlatformTransactionManager txManager) {
        TransactionInterceptor txInterceptor = transactionInterceptor(txManager);
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(Service.class, true);
        return new DefaultPointcutAdvisor(pointcut, txInterceptor);
    }
}