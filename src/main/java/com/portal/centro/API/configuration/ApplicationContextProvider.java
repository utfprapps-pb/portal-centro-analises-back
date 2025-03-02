package com.portal.centro.API.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return ApplicationContextProvider.context;
    }

    public static <T> T getBean(Class<T> bean) throws BeansException {
        return getApplicationContext().getBean(bean);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        ApplicationContextProvider.context = ctx;
    }

}
