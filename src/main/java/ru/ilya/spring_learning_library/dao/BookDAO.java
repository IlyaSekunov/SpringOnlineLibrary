package ru.ilya.spring_learning_library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ilya.spring_learning_library.model.Book;
import ru.ilya.spring_learning_library.model.Person;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDAO {

  private final JdbcTemplate jdbcTemplate;

  public List<Book> getBooksByPersonId(int id) {
    return jdbcTemplate.query(
        "select * from book where person_id = ?",
        new Object[] {id},
        new BeanPropertyRowMapper<>(Book.class));
  }

  public List<Book> getAllBooks() {
    return jdbcTemplate.query(
        "select * from book",
        new BeanPropertyRowMapper<>(Book.class) {
          {
            setPrimitivesDefaultedForNullValue(true);
          }
        });
  }

  public void addBook(Book book) {
    jdbcTemplate.update(
        "insert into book(title, author, publish_year) values(?, ?, ?)",
        book.getTitle(),
        book.getAuthor(),
        book.getPublishYear());
  }

  public Book getBookByBookId(int id) {
    BeanPropertyRowMapper<Book> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Book.class);
    beanPropertyRowMapper.setPrimitivesDefaultedForNullValue(true);
    return jdbcTemplate
        .query("select * from book where book_id = ?", new Object[] {id}, beanPropertyRowMapper)
        .stream()
        .findAny()
        .orElse(null);
  }

  public Person getBookOwnerByBookId(int bookId) {
    return jdbcTemplate
        .query(
            "select person.* from person join book on book.person_id = person.person_id where"
                + " book.book_id = ?",
            new Object[] {bookId},
            new BeanPropertyRowMapper<>(Person.class))
        .stream()
        .findAny()
        .orElse(null);
  }

  public void updateBookOwner(int bookId, int bookOwnerId) {
    jdbcTemplate.update("update book set person_id = ? where book_id = ?", bookOwnerId, bookId);
  }

  public void updateBook(int id, Book book) {
    jdbcTemplate.update(
        "update book set title = ?, author = ?, publish_year = ? where book_id = ?",
        book.getTitle(),
        book.getAuthor(),
        book.getPublishYear(),
        id);
  }

  public void deleteBook(int id) {
    jdbcTemplate.update("delete from book where book_id = ?", id);
  }

  public void deleteBookOwner(int bookId) {
    jdbcTemplate.update("update book set person_id = null where book_id = ?", bookId);
  }
}
