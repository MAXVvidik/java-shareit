package ru.practicum.shareit.user.validate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

@Slf4j
@Component
public class ValidateUserData {

    private User user;

    private void setUser(User user) {
        this.user = user;
    }

    public boolean isCorrectName() { // proverka name
        if (!user.getName().isEmpty() && !user.getName().contains(" ")) {
            return true;
        } else {
            log.warn("Ошибка во входных данных. Логин пустой или содержит пробелы");
            return false;
        }
    }

    public boolean isCorrectEmail() { // proverka email
        if (user.getEmail() != null && !user.getEmail().isEmpty() && user.getEmail().contains("@")) {
            return true;
        } else {
            log.warn("Ошибка во входных данных. Электронная почта пустая или не содержит @");
            return false;
        }
    }

    public boolean checkAllData(User user) { // proverka name and email vmeste
        setUser(user);
        if (isCorrectEmail() && isCorrectName()) {
            return true;
        } else {
            return false;
        }
    }

}
