package ru.ilya.spring_learning_library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "username")
  @Size(min = 3, max = 100, message = "Username should be between 3 and 100 characters")
  private String username;

  @Column(name = "password")
  @Size(min = 8, message = "Password cannot has len less than 8 characters")
  private String password;

  @Column(name = "year_of_birth")
  @Min(value = 1900, message = "Year of birth cannot be less than 1900")
  private int yearOfBirth;
}
