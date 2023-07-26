package ru.ilya.spring_learning_library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Person")
@Data
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @Column(name = "fio")
    @NotEmpty(message = "Поле ФИО не должно быть пустым")
    @Pattern(regexp = "([А-ЯЁA-Z][а-яёa-z]+[\\-\\s]?){3,}", message = "Поле должно соответствовать: Фамилия Имя Отчество")
    private String fio;

    @Column(name = "birthday")
    @NotNull(message = "Поле год рождения не должно быть пустым")
    @Min(value = 1900, message = "Год рождения не может быть меньше 1900г")
    private int birthday;

    @OneToMany(mappedBy = "bookOwner")
    private List<Book> books;

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }
}
