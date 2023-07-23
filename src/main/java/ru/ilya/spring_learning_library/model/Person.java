package ru.ilya.spring_learning_library.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private int personId;

    @NotEmpty(message = "Поле ФИО не должно быть пустым")
    @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){3,}", message = "Поле должно соответствовать: Фамилия Имя Отчество")
    private String fio;

    @NotNull(message = "Поле год рождения не должно быть пустым")
    @Min(value = 1900, message = "Год рождения не может быть меньше 1900г")
    private int birthday;
}
