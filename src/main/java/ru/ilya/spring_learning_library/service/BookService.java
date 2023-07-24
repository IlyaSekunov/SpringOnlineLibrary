package ru.ilya.spring_learning_library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.spring_learning_library.model.Book;
import ru.ilya.spring_learning_library.model.Person;
import ru.ilya.spring_learning_library.repository.BookRepository;
import ru.ilya.spring_learning_library.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

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
        return bookRepository.findAllByBookOwner(bookOwner);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book findById(int bookId) {
        return bookRepository.findById(bookId).orElse(null);
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
            person.addBook(book);
            bookRepository.save(book); //Посмотреть настройку каскадирования
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
            bookOwner.removeBook(book);
            bookRepository.save(book);
        }
    }
}
