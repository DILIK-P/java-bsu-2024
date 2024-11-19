package by.bsu.dependency.context;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.PostConstruct;
import by.bsu.dependency.exceptions.ApplicationContextNotStartedException;
import by.bsu.dependency.exceptions.NoSuchBeanDefinitionException;

public abstract class AbstractApplicationContext implements ApplicationContext {

    protected enum ContextStatus {
        NOT_STARTED,
        STARTED
    }

    protected Map<String, Class<?>> beanDefinitions;
    protected final Map<String, Object> singletonBeans = new HashMap<>();
    protected ContextStatus contextStatus = ContextStatus.NOT_STARTED;

    public AbstractApplicationContext(Stream<Class<?>> beanClasses) {
        this.beanDefinitions = beanClasses.collect(
                Collectors.toMap(
                        beanClass -> {
                            if (beanClass.isAnnotationPresent(Bean.class)
                                    && !"".equals(beanClass.getAnnotation(Bean.class).name())) {
                                return beanClass.getAnnotation(Bean.class).name();
                            } else {
                                return Character.toLowerCase(beanClass.getSimpleName().charAt(0))
                                        + beanClass.getSimpleName().substring(1);
                            }
                        },
                        Function.identity()));
    }

    @Override
    public void start() {
        beanDefinitions.forEach((beanName, beanClass) -> {
            if (isSingleton(beanName)) {
                singletonBeans.put(beanName, instantiateBean(beanClass));
            }
        });
        contextStatus = ContextStatus.STARTED;

        singletonBeans.forEach((beanName, bean) -> inject(beanName, bean));
        singletonBeans.forEach((beanName, bean) -> callPostConstruct(beanName, bean));
    }

    @Override
    public boolean isRunning() {
        return contextStatus == ContextStatus.STARTED;
    }

    @Override
    public boolean containsBean(String name) {
        if (!isRunning()) {
            throw new ApplicationContextNotStartedException("Application context hasn't started");
        }

        return beanDefinitions.containsKey(name);
    }

    @Override
    public Object getBean(String name) {
        if (!isRunning()) {
            throw new ApplicationContextNotStartedException("Application context hasn't started");
        }

        if (!containsBean(name)) {
            throw new NoSuchBeanDefinitionException("No bean with such name");
        }

        if (isSingleton(name)) {
            return singletonBeans.get(name);
        } else {
            Object bean = instantiateBean(beanDefinitions.get(name));
            inject(name, bean);
            callPostConstruct(name, bean);
            return bean;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(Class<T> clazz) {
        if (!isRunning()) {
            throw new ApplicationContextNotStartedException("Application context hasn't started");
        }

        for (Map.Entry<String, Class<?>> entry : beanDefinitions.entrySet()) {
            if (clazz == entry.getValue()) {
                return (T) getBean(entry.getKey());
            }
        }

        throw new NoSuchBeanDefinitionException("No bean with such class");
    }

    protected <T> T instantiateBean(Class<T> beanClass) {
        try {
            return beanClass.getConstructor().newInstance();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException
                | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    protected void inject(String beanName, Object bean) {
        Arrays.stream(beanDefinitions.get(beanName).getDeclaredFields())
                .toList()
                .forEach(
                        field -> {
                            if (field.isAnnotationPresent(Inject.class)) {
                                try {
                                    field.setAccessible(true);
                                    field.set(bean, getBean(field.getType()));
                                } catch (IllegalArgumentException | IllegalAccessException ignored) {
                                }
                            }
                        });
    }

    protected void callPostConstruct(String beanName, Object bean) {
        Arrays.stream(beanDefinitions.get(beanName).getDeclaredMethods())
                .toList()
                .forEach(
                        method -> {
                            if (method.isAnnotationPresent(PostConstruct.class)) {
                                try {
                                    method.setAccessible(true);
                                    method.invoke(bean);
                                } catch (IllegalAccessException | InvocationTargetException ignored) {
                                }
                            }
                        });
    }

    @Override
    public boolean isPrototype(String name) {
        return !isSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        if (!beanDefinitions.containsKey(name)) {
            throw new NoSuchBeanDefinitionException("No bean with such name");
        }

        Class<?> beanDefinition = beanDefinitions.get(name);
        return !beanDefinition.isAnnotationPresent(Bean.class)
                || beanDefinition.getAnnotation(Bean.class).scope() == BeanScope.SINGLETON;
    }
}
