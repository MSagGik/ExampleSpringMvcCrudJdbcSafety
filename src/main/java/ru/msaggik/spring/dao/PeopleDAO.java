package ru.msaggik.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.msaggik.spring.models.Person;

import java.util.List;

@Component
public class PeopleDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() { // возврат всех пользователей из БД
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        // так как названия полей совпадают с названием колонок таблицы БД,
        // то можно не создавать PersonMapper(), а использовать стандартный из jdbcTemplate
        // пример: return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        // вместо примера: return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[] {id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
        // если запрошенных данных нет, то  ".stream().findAny().orElse(null)" возвращает null
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person VALUES(1, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail());
    }
    // обновление данных пользователя
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }
    // удаление данных пользователя
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
