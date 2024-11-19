package by.bsu.dependency.myexample;

import by.bsu.dependency.context.ApplicationContext;
import by.bsu.dependency.context.AutoScanApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AutoScanApplicationContext("by.bsu.dependency.myexample");

        System.out.println(
                "Однажды Рябый Вячеслав Васильевич🧓 объяснял первой группе🤓 материал курса операционных систем🖥️.");
        System.out.println("Первая группа хоть убей не понимала😭!");
        System.out.println("Тогда ему пришлось привести следущую историю-аналогию🤔:");
        System.out.println();

        applicationContext.start();
        System.out.println();
        ((KingRiabyBean) applicationContext.getBean("kingRiabyBean")).giveCommand();
        System.out.println();

        System.out.println("Первая группа ничего не поняла, но к счастью пара уже закончилась!🤠🤠🤠");
    }
}
