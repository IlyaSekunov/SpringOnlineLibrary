package ru.ilya.spring_learning_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilya.spring_learning_library.model.Book;
import ru.ilya.spring_learning_library.model.Person;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
            List<Book> findAllByBookOwner(Person bookOwner);
                List<Book> findByTitleContaining(String title);
}
