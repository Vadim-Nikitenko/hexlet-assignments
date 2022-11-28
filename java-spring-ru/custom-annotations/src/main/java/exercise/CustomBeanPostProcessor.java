package exercise;

import exercise.calculator.Calculator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Arrays;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    org.slf4j.Logger logger = LoggerFactory.getLogger(CustomBeanPostProcessor.class);
    String level;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            level = bean
                    .getClass()
                    .getAnnotation(Inspect.class)
                    .level();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            return Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        if (level.equals("info")) {
                            logger.info(String.format("Was called method: %s() with arguments: %s", method.getName(), Arrays.toString(args)));
                        } else {
                            logger.debug(String.format("Was called method: %s() with arguments: %s", method.getName(), Arrays.toString(args)));
                        }
                        return method.invoke(bean, args);
                    }
            );
        }
        return bean;
    }


}
