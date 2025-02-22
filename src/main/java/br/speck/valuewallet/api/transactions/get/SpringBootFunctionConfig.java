package br.speck.valuewallet.api.transactions.get;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Inicializa o contexto do Spring Boot dentro da Azure Function
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringBootFunctionConfig {

    private static ApplicationContext context;

    static {
        context = SpringApplication.run(SpringBootFunctionConfig.class);
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }
}

