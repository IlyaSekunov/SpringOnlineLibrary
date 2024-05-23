package ru.ilya.spring_learning_library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ilya.spring_learning_library.model.User;
import ru.ilya.spring_learning_library.service.UserDetailsServiceImpl;
import ru.ilya.spring_learning_library.utill.UserValidator;

@Controller
@RequiredArgsConstructor
public class AuthController {

  private final UserDetailsServiceImpl userDetailsService;
  private final UserValidator userValidator;

  @GetMapping("/registration")
  public String registerPage(@ModelAttribute("user") User user) {
    return "auth/registration";
  }

  @PostMapping("/registration")
  public String confirmRegistration(
      @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
    userValidator.validate(user, bindingResult);
    if (bindingResult.hasErrors()) {
      return "auth/registration";
    }

    userDetailsService.save(user);
    return "redirect:/people";
  }
}
