package by.bsu.dependency.example;

import by.bsu.dependency.context.ApplicationContext;
import by.bsu.dependency.context.HardCodedSingletonApplicationContext;
import by.bsu.dependency.context.SimpleApplicationContext;
import by.bsu.dependency.example.beanpackage.FirstBean;
import by.bsu.dependency.example.beanpackage.OtherBean;
import by.bsu.dependency.example.beanpackage.UnannotatedBean;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new HardCodedSingletonApplicationContext(
                FirstBean.class, OtherBean.class);
        applicationContext.start();

        FirstBean firstBean = (FirstBean) applicationContext.getBean("firstBean");
        OtherBean otherBean = (OtherBean) applicationContext.getBean("otherBean");

        firstBean.doSomething();
        otherBean.doSomething();

        ApplicationContext anotherApplicationContext = new SimpleApplicationContext(
                UnannotatedBean.class, PrototypeBean.class);
        anotherApplicationContext.start();

        PrototypeBean firstPrototypeBean = (PrototypeBean) anotherApplicationContext.getBean("prototypeBean");
        PrototypeBean secondPrototypeBean = (PrototypeBean) anotherApplicationContext.getBean(PrototypeBean.class);

        firstPrototypeBean.doSomething();
        secondPrototypeBean.doSomething();
    }
}
