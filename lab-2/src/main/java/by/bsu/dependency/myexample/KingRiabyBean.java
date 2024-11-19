package by.bsu.dependency.myexample;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.Inject;
import by.bsu.dependency.annotation.PostConstruct;

@Bean()
public class KingRiabyBean {

    @Inject
    SoldierRiabyBean soldier1;
    @Inject
    SoldierRiabyBean soldier2;
    @Inject
    SoldierRiabyBean soldier3;

    @Inject
    BulletBoxBean bulletBox;

    @PostConstruct
    void init() {
        System.out.println("На поле появляется король Рябый🫅!");
    }

    void giveCommand() {
        System.out.println("Король Рябый🫅 даёт команду стрелять по кроликам!");

        while (bulletBox.isThereBullets()) {

            System.out.println("Король Рябый🫅 даёт команду солдату Рябому №1️🫡!");
            soldier1.attack();
            System.out.println();

            System.out.println("Король Рябый🫅 даёт команду солдату Рябому №2️🫡!");
            soldier2.attack();
            System.out.println();

            System.out.println("Король Рябый🫅 даёт команду солдату Рябому №3️🫡!");
            soldier3.attack();
            System.out.println();
        }

        System.out.println("Кажется патроны закончились. Отличная охота!🥳");
    }
}
