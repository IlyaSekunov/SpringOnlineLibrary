package ru.ilya.spring_learning_library.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private int bookId;
    private int personId;

    @NotEmpty(message = "Название книги не должно быть пустым")
    private String title;

    @NotEmpty(message = "Книга должна иметь автора")
    @Pattern(regexp = "([А-ЯЁ][а-яё]+[\\-\\s]?){2,}", message = "Некорректно введены данные автора")
    private String author;

    @NotNull(message = "Год публикации должен быть указан")
    private int publishYear;
}
