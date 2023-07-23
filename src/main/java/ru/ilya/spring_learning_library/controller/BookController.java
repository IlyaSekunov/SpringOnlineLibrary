package ru.ilya.spring_learning_library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ilya.spring_learning_library.dao.BookDAO;
import ru.ilya.spring_learning_library.model.Book;
import ru.ilya.spring_learning_library.dao.PersonDAO;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @GetMapping
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "books/allBooks";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book) {
        return "books/addBook";
    }

    @PostMapping
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/addBook";

        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBookByBookId(id));
        model.addAttribute("people", personDAO.getAllPeople());
        model.addAttribute("bookOwner", bookDAO.getBookOwnerByBookId(id));
        return "books/showBook";
    }

    @PatchMapping("/{id}/update_owner")
    public String updateBookOwner(@PathVariable("id") int bookId,
                               @RequestParam("newOwner") int newOwnerId) {
        bookDAO.updateBookOwner(bookId, newOwnerId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/reset_owner")
    public String resetBookOwner(@PathVariable("id") int bookId) {
        bookDAO.deleteBookOwner(bookId);
        return "redirect:/books/" + bookId;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBookByBookId(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/editBook";

        bookDAO.updateBook(id, book);
        return "redirect:/books/showBook";
    }
}
