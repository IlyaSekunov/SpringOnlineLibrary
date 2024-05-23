package ru.ilya.spring_learning_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilya.spring_learning_library.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {}
