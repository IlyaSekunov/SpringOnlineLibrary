package ru.ilya.spring_learning_library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilya.spring_learning_library.model.Person;
import ru.ilya.spring_learning_library.repository.PersonRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private Person person;

    @InjectMocks
    private PersonService personService;

    @Test
    void findPersonById_ReturnsNullIfUserIsNotFound() {
        when(personRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThat(personService.findPersonById(213)).isNull();
    }

    @Test
    void findPersonById_ReturnsNotNullIfUserIsNotFound() {
        when(personRepository.findById(anyInt())).thenReturn(Optional.of(person));
        assertThat(personService.findPersonById(213)).isNotNull();
        assertThat(personService.findPersonById(213)).isInstanceOf(Person.class);
    }
}
