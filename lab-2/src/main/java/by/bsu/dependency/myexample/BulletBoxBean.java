package by.bsu.dependency.myexample;

import java.util.Random;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.PostConstruct;

@Bean()
public class BulletBoxBean {
    Integer bulletCnt;

    @PostConstruct
    void init() {
        bulletCnt = new Random().nextInt(10);
        System.out.println("Создан ящик с " + bulletCnt.toString() + " патронами💣!");
    }

    boolean isThereBullets() {
        return bulletCnt > 0;
    }

    void deleteBullet() {
        if (isThereBullets()) {
            bulletCnt--;
            System.out.println("-1 патрон. Осталось " + bulletCnt.toString() + " патрон💣");
        } else {
            throw new RuntimeException("No bullets left");
        }
    }
}
