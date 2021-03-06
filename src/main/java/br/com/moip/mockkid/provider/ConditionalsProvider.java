package br.com.moip.mockkid.provider;

import br.com.moip.mockkid.conditional.ConditionalSolver;
import br.com.moip.mockkid.model.Conditionals;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class ConditionalsProvider {

    private static final Logger logger = LoggerFactory.getLogger(ConditionalsProvider.class);

    @Bean
    public Conditionals getConditionals() {
        return loadConditionalExpressionClasses();
    }

    private Conditionals loadConditionalExpressionClasses() {
        Reflections reflections = new Reflections("br.com.moip.mockkid");
        Set<Class<? extends ConditionalSolver>> conditionals = reflections.getSubTypesOf(ConditionalSolver.class);
        Conditionals instances = new Conditionals();

        for (Class<? extends ConditionalSolver> conditional : conditionals) {
            try {
                instances.add(conditional.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                logger.error("Couldn't instantiate class " + conditional.getName() + ", skipping...", e);
            }
        }

        return instances;
    }

}
