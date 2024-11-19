package by.bsu.dependency.example.beanpackage;

public class UnannotatedBean {
    Integer counter = 0;

    void printSomething() {
        System.out.println("Hello, I'm unannotated bean");
    }

    public void doSomething() {
        System.out.println("Unannotated bean is adding one to counter");
        counter++;
        System.out.println("Counter value: " + counter.toString());
    }
}
