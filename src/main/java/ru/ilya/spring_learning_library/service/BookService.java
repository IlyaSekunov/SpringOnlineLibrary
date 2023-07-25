package ru.ilya.spring_learning_library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.spring_learning_library.model.Book;
import ru.ilya.spring_learning_library.model.Person;
import ru.ilya.spring_learning_library.repository.BookRepository;
import ru.ilya.spring_learning_library.repository.PersonRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    public List<Book> findBooksByOwnerId(int personId) {
        Optional<Person> bookOwnerOptional = personRepository.findById(personId);
        Person bookOwner = null;
        if (bookOwnerOptional.isPresent()) {
            bookOwner = bookOwnerOptional.get();
        }
        List<Book> books = bookRepository.findAllByBookOwner(bookOwner);
        checkExpiredReservations(books);
        return books;
    }

    public List<Book> findAll(int page, int itemsPerPage, boolean sortByYear) {
        List<Book> books;
        if (page > 0 && itemsPerPage > 0) {
            if (sortByYear) {
                books = bookRepository.findAll(PageRequest.of(page - 1, itemsPerPage, Sort.by("publishYear"))).getContent();
                checkExpiredReservations(books);
                return books;
            } else {
                books = bookRepository.findAll(PageRequest.of(page - 1, itemsPerPage)).getContent();
                checkExpiredReservations(books);
                return books;
            }
        } else if (sortByYear) {
            books = bookRepository.findAll(Sort.by("publishYear"));
            checkExpiredReservations(books);
            return books;
        } else {
            books = bookRepository.findAll();
            checkExpiredReservations(books);
            return books;
        }
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book findById(int bookId) {
        Book book = null;
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
            if (book.getBookOwner() != null) {
                checkExpiredReservations(List.of(book));
            }
        }
        return book;
    }

    public Person getBookOwnerByBookId(int bookId) {
        Person bookOwner = null;
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            bookOwner = book.getBookOwner();
        }
        return bookOwner;
    }

    @Transactional
    public void updateBookOwner(int bookId, int personId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<Person> personOptional = personRepository.findById(personId);
        if (bookOptional.isPresent() && personOptional.isPresent()) {
            Book book = bookOptional.get();
            Person person = personOptional.get();
            book.setBookOwner(person);
            book.setReservationTime(new Date());
            person.addBook(book);
            bookRepository.save(book);
        }
    }

    @Transactional
    public void update(int bookId, Book book) {
        book.setBookId(bookId);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int bookId) {
        bookRepository.deleteById(bookId);
    }

    @Transactional
    public void deleteBookOwnerByBookId(int bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            Person bookOwner = book.getBookOwner();
            book.setBookOwner(null);
            book.setReservationTime(null);
            bookOwner.removeBook(book);
            bookRepository.save(book);
        }
    }

    public List<Book> findBooksContaining(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        checkExpiredReservations(books);
        return books;
    }

    public void checkExpiredReservations(List<Book> books) {
        for (Book book : books) {
            if (book.getBookOwner() != null) {
                Date reservationTime = book.getReservationTime();
                Date currentTime = new Date();
                long timeDiff = currentTime.getTime() - reservationTime.getTime();
                long daysDiff = TimeUnit.MILLISECONDS.toDays(timeDiff);
                book.setExpired(daysDiff >= 10);
            }
        }
    }
}
