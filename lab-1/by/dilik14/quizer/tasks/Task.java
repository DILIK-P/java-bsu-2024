package by.dilik14.quizer.tasks;

import by.dilik14.quizer.Result;

/**
 * Interface, который описывает одно задание
 */
public interface Task {
    /**
     @return текст задания
     */
    String getText();
    
    /**
     * Проверяет ответ на задание и возвращает результат
     *
     * @param  answer ответ на задание
     * @return        результат ответа
     * @see           Result
     */
    Result validate(String answer);
}
