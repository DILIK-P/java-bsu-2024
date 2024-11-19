package by.bsu.dependency.myexample;

import java.util.Random;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.PostConstruct;

@Bean()
public class FieldBean {
    Integer rabbitCnt;

    @PostConstruct
    void init() {
        rabbitCnt = new Random().nextInt(5);
        System.out.println("Создано поле с " + rabbitCnt.toString() + " кроликами🐰!");
    }

    boolean isThereRabbits() {
        return rabbitCnt > 0;
    }

    void killRabbit() {
        if (isThereRabbits()) {
            rabbitCnt--;
            System.out.println("Ой ой! Убит кролик!😔 Осталось " + rabbitCnt.toString() + " кроликов🐰");
        } else {
            System.out.println("Но ведь кроликов уже не осталось... Видимо у кого-то галлюцинации🫨...");
        }
    }
}
