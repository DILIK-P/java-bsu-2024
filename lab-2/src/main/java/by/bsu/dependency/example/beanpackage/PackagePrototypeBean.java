package by.bsu.dependency.example.beanpackage;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.Inject;

@Bean(scope = BeanScope.PROTOTYPE)
public class PackagePrototypeBean {
    @Inject
    FirstBean firstBean;

    void printSomething() {
        System.out.println("Hello, I'm package prototype bean");
    }

    public void doSomething() {
        System.out.println("Prototype bean is doing very important job!");
    }
}
