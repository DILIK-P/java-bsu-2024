package by.bsu.dependency.example.beanpackage;

import by.bsu.dependency.annotation.Bean;

@Bean(name = "firstBean")
public class FirstBean {

    void printSomething() {
        System.out.println("Hello, I'm first bean");
    }

    public void doSomething() {
        System.out.println("First bean is working on a project...");
    }
}
