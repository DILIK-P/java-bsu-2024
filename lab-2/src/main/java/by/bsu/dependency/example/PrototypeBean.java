package by.bsu.dependency.example;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.example.beanpackage.UnannotatedBean;

@Bean(scope = BeanScope.PROTOTYPE)
public class PrototypeBean {
    @Inject
    UnannotatedBean unannotatedBean;

    void printSomething() {
        System.out.println("Hello, I'm prototype bean");
    }

    public void doSomething() {
        System.out.println("Prototype bean is adding one to unannotated bean!");
        unannotatedBean.doSomething();
    }
}
