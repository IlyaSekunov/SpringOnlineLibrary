package ru.ilya.spring_learning_library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ilya.spring_learning_library.model.Book;
import ru.ilya.spring_learning_library.service.BookService;
import ru.ilya.spring_learning_library.service.PersonService;
import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @GetMapping
    public String showAllBooks(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "books_per_page", required = false, defaultValue = "0") int booksPerPage,
            @RequestParam(value = "sort_by_year", required = false, defaultValue = "false") boolean sortByYear,
            Model model) {

        model.addAttribute("books", bookService.findAll(page, booksPerPage, sortByYear));
        return "books/allBooks";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book) {
        return "books/addBook";
    }

    @PostMapping
    public String addBook(@Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/addBook";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookService.findById(bookId));
        model.addAttribute("people", personService.findAll());
        model.addAttribute("bookOwner", bookService.getBookOwnerByBookId(bookId));
        return "books/showBook";
    }

    @PatchMapping("/{id}/update_owner")
    public String updateBookOwner(@PathVariable("id") int bookId,
                               @RequestParam("newOwner") int newOwnerId) {
        bookService.updateBookOwner(bookId, newOwnerId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/reset_owner")
    public String resetBookOwner(@PathVariable("id") int bookId) {
        bookService.deleteBookOwnerByBookId(bookId);
        return "redirect:/books/" + bookId;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int bookId) {
        bookService.delete(bookId);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookService.findById(bookId));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int bookId,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/editBook";

        bookService.update(bookId, book);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(value = "search_string", required = false) String searchString, Model model) {
        List<Book> books = null;
        if (searchString != null) {
            books = bookService.findBooksContaining(searchString);
        }
        model.addAttribute("books", books);
        return "books/searchBook";
    }
}
