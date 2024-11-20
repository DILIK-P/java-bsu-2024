package by.bsu.dependency.context;

import java.util.Arrays;

import by.bsu.dependency.annotation.Bean;
import by.bsu.dependency.annotation.BeanScope;

public class HardCodedSingletonApplicationContext extends AbstractApplicationContext {
    /**
     * ! Класс существует только для базового примера !
     * <br/>
     * Создает контекст, содержащий классы, переданные в параметре. Полагается на
     * отсутсвие зависимостей в бинах,
     * а также на наличие аннотации {@code @Bean} на переданных классах.
     * <br/>
     * ! Контекст данного типа не занимается внедрением зависимостей !
     * <br/>
     * ! Создает только бины со скоупом {@code SINGLETON} !
     *
     * @param beanClasses классы, из которых требуется создать бины
     */
    public HardCodedSingletonApplicationContext(Class<?>... beanClasses) {
        super(Arrays.stream(beanClasses).filter(beanClass -> beanClass.isAnnotationPresent(Bean.class)
                && beanClass.getAnnotation(Bean.class).scope() == BeanScope.SINGLETON), false);
    }
}
