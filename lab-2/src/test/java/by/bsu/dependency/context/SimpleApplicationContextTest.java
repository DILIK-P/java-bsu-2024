package by.bsu.dependency.context;

import by.bsu.dependency.example.PrototypeBean;
import by.bsu.dependency.example.beanpackage.FirstBean;
import by.bsu.dependency.example.beanpackage.OtherBean;
import by.bsu.dependency.example.beanpackage.UnannotatedBean;
import by.bsu.dependency.exceptions.ApplicationContextNotStartedException;
import by.bsu.dependency.exceptions.NoSuchBeanDefinitionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleApplicationContextTest {

    private ApplicationContext applicationContext;

    @BeforeEach
    void init() {
        applicationContext = new SimpleApplicationContext(FirstBean.class, OtherBean.class, PrototypeBean.class,
                UnannotatedBean.class);
    }

    @Test
    void testIsRunning() {
        assertThat(applicationContext.isRunning()).isFalse();
        applicationContext.start();
        assertThat(applicationContext.isRunning()).isTrue();
    }

    @Test
    void testContextContainsNotStarted() {
        assertThrows(
                ApplicationContextNotStartedException.class,
                () -> applicationContext.containsBean("firstBean"));
    }

    @Test
    void testContextContainsBeans() {
        applicationContext.start();

        assertThat(applicationContext.containsBean("firstBean")).isTrue();
        assertThat(applicationContext.containsBean("otherBean")).isTrue();
        assertThat(applicationContext.containsBean("randomName")).isFalse();
    }

    @Test
    void testContextContainsPrototypeBeans() {
        applicationContext.start();

        assertThat(applicationContext.containsBean("prototypeBean")).isTrue();
    }

    @Test
    void testContextContainsUnannotatedBeans() {
        applicationContext.start();

        assertThat(applicationContext.containsBean("unannotatedBean")).isTrue();
    }

    @Test
    void testContextGetBeanNotStarted() {
        assertThrows(
                ApplicationContextNotStartedException.class,
                () -> applicationContext.getBean("firstBean"));
    }

    @Test
    void testGetBeanReturns() {
        applicationContext.start();

        assertThat(applicationContext.getBean("firstBean")).isNotNull().isInstanceOf(FirstBean.class);
        assertThat(applicationContext.getBean("otherBean")).isNotNull().isInstanceOf(OtherBean.class);
    }

    @Test
    void testGetPrototypeBeanReturns() {
        applicationContext.start();

        assertThat(applicationContext.getBean("prototypeBean")).isNotNull().isInstanceOf(PrototypeBean.class);
    }

    @Test
    void testGetUnannotatedBeanReturns() {
        applicationContext.start();

        assertThat(applicationContext.getBean("unannotatedBean")).isNotNull().isInstanceOf(UnannotatedBean.class);
    }

    @Test
    void testGetBeanThrows() {
        applicationContext.start();

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> applicationContext.getBean("randomName"));
    }

    @Test
    void testInjections() {
        applicationContext.start();

        PrototypeBean prototypeBean = (PrototypeBean) applicationContext.getBean("prototypeBean");
        prototypeBean.doSomething();
    }

    @Test
    void testSingletonEquality() {
        applicationContext.start();

        FirstBean firstFirstBean = (FirstBean) applicationContext.getBean("firstBean");
        FirstBean secondFirstBean = (FirstBean) applicationContext.getBean("firstBean");

        assertThat(firstFirstBean).isSameAs(secondFirstBean);
    }

    @Test
    void testPrototypeInequality() {
        applicationContext.start();

        PrototypeBean firstPrototypeBean = (PrototypeBean) applicationContext.getBean("prototypeBean");
        PrototypeBean secondPrototypeBean = (PrototypeBean) applicationContext.getBean(PrototypeBean.class);

        assertThat(firstPrototypeBean).isNotSameAs(secondPrototypeBean);
    }

    @Test
    void testIsSingletonReturns() {
        assertThat(applicationContext.isSingleton("firstBean")).isTrue();
        assertThat(applicationContext.isSingleton("otherBean")).isTrue();
        assertThat(applicationContext.isSingleton("prototypeBean")).isFalse();
        assertThat(applicationContext.isSingleton("unannotatedBean")).isTrue();
    }

    @Test
    void testIsSingletonThrows() {
        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> applicationContext.isSingleton("randomName"));
    }

    @Test
    void testIsPrototypeReturns() {
        assertThat(applicationContext.isPrototype("firstBean")).isFalse();
        assertThat(applicationContext.isPrototype("otherBean")).isFalse();
        assertThat(applicationContext.isPrototype("prototypeBean")).isTrue();
        assertThat(applicationContext.isPrototype("unannotatedBean")).isFalse();
    }

    @Test
    void testIsPrototypeThrows() {
        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> applicationContext.isPrototype("randomName"));
    }
}
