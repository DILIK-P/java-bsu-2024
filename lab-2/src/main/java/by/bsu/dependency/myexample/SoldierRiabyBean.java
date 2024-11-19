package by.bsu.dependency.myexample;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.PostConstruct;

@Bean(scope = BeanScope.PROTOTYPE)
public class SoldierRiabyBean {

    @Inject
    FieldBean field;
    @Inject
    BulletBoxBean bulletBox;

    @PostConstruct
    void init() {
        System.out.println("На поле появляется солдат Рябый🪖!");
    }

    void attack() {
        if (Math.random() > 0.7 && bulletBox.isThereBullets()) {
            System.out.println("Солдат Рябый🪖 попадает в кролика🐰!");
            bulletBox.deleteBullet();
            field.killRabbit();
        } else if (!bulletBox.isThereBullets()) {
            System.out.println("Почему солдат Рябый🪖 продолжают стрелять, если патрон уже нет🔫?...");
        } else {
            System.out.println("Солдат Рябый🪖 не попал...");
            bulletBox.deleteBullet();
        }
    }
}
