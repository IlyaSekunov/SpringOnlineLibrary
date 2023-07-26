package ru.ilya.spring_learning_library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.spring_learning_library.model.Person;
import ru.ilya.spring_learning_library.repository.PersonRepository;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    public Person findPersonById(int personId) {
        return personRepository.findById(personId).orElse(null);
    }

    @Transactional
    public void update(int personId, Person person) {
        person.setPersonId(personId);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int personId) {
        personRepository.deleteById(personId);
    }
}
