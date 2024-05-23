package ru.ilya.spring_learning_library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ilya.spring_learning_library.model.Person;
import ru.ilya.spring_learning_library.service.BookService;
import ru.ilya.spring_learning_library.service.PersonService;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

  private final PersonService personService;
  private final BookService bookService;

  @GetMapping
  public String showAllPeople(Model model) {
    model.addAttribute("people", personService.findAll());
    return "people/allPeople";
  }

  @GetMapping("/new")
  public String addPerson(@ModelAttribute("person") Person person) {
    return "people/addPerson";
  }

  @PostMapping
  public String addPerson(@Valid Person person, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) return "people/addPerson";

    personService.save(person);
    return "redirect:/people";
  }

  @GetMapping("/{id}")
  public String showPerson(@PathVariable("id") int personId, Model model) {
    model.addAttribute("person", personService.findPersonById(personId));
    model.addAttribute("books", bookService.findBooksByOwnerId(personId));
    return "people/showPerson";
  }

  @GetMapping("/{id}/edit")
  public String editPerson(@PathVariable("id") int personId, Model model) {
    model.addAttribute("person", personService.findPersonById(personId));
    return "people/editPerson";
  }

  @PatchMapping("/{id}")
  public String editPerson(
      @PathVariable("id") int personId,
      @ModelAttribute("person") @Valid Person person,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) return "people/editPerson";

    personService.update(personId, person);
    return "redirect:/people/" + personId;
  }

  @DeleteMapping("/{id}")
  public String deletePerson(@PathVariable("id") int personId) {
    personService.delete(personId);
    return "redirect:/people";
  }
}
