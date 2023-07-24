package ru.ilya.spring_learning_library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ilya.spring_learning_library.model.Person;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public List<Person> getAllPeople() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void savePerson(Person person) {
        jdbcTemplate.update("insert into person(fio, birthday) values(?, ?)", person.getFio(), person.getBirthday());
    }

    public Person getPersonById(int id) {
        return jdbcTemplate.query("select * from person where person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void updatePerson(int id, Person person) {
        jdbcTemplate.update("update person set fio=?, birthday=? where person_id = ?",
                person.getFio(), person.getBirthday(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("delete from person where person_id = ?", id);
    }
}
