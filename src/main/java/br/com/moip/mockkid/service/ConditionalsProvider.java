package br.com.moip.mockkid.service;

import br.com.moip.mockkid.conditional.ConditionalExpression;
import br.com.moip.mockkid.conditional.Conditionals;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ConditionalsProvider {

    private static final Logger logger = LoggerFactory.getLogger(ConditionalsProvider.class);

    @Bean
    private Conditionals conditionals() {
        Reflections reflections = new Reflections("br.com.moip.mockkid");
        Set<Class<? extends ConditionalExpression>> conditionals = reflections.getSubTypesOf(ConditionalExpression.class);
        Conditionals instances = new Conditionals();

        for (Class<? extends ConditionalExpression> conditional : conditionals) {
            try {
                instances.add(conditional.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                logger.error("Couldn't instantiate class " + conditional.getName() + ", skipping...", e);
            }
        }

        return instances;
    }

}