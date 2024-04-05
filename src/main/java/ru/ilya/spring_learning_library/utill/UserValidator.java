package ru.ilya.spring_learning_library.utill;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ilya.spring_learning_library.model.User;
import ru.ilya.spring_learning_library.service.UserDetailsServiceImpl;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userDetailsService.isUserExists(user.getUsername())) {
            errors.rejectValue("username", "", "User with this username already exists");
        }
    }
}
