package ru.ilya.spring_learning_library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "Book")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person bookOwner;

    @Column(name = "title")
    @NotEmpty(message = "Название книги не должно быть пустым")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Книга должна иметь автора")
    @Pattern(regexp = "([А-ЯЁA-Z][а-яёa-z]+[\\-\\s]?){2,}", message = "Некорректно введены данные автора")
    private String author;

    @Column(name = "publish_year")
    @NotNull(message = "Год публикации должен быть указан")
    private int publishYear;

    @Column(name = "reservation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationTime;

    @Transient
    private boolean isExpired;
}
