package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {
    private static final MemoryVacancyRepository INSTANCE = new MemoryVacancyRepository();

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer",
                "Assist in developing basic modules, fix minor bugs, write simple unit tests. Great opportunity for students to learn Spring Boot and Git.",
                LocalDateTime.of(2026, 1, 15, 10, 0)));
        save(new Vacancy(0, "Junior Java Developer",
                "Develop REST APIs, work with PostgreSQL, write integration tests. 1+ year of commercial experience with Java Core and Spring is required.",
                LocalDateTime.of(2026, 2, 10, 14, 30)));
        save(new Vacancy(0, "Junior+ Java Developer",
                "Design microservices, optimize SQL queries, participate in code reviews. Strong knowledge of Spring Boot, Hibernate, and Docker.",
                LocalDateTime.of(2026, 3, 5, 9, 15)));
        save(new Vacancy(0, "Middle Java Developer",
                "Lead small features end-to-end, mentor junior developers, design database schemas. Hands-on experience with Kafka, Redis, and CI/CD pipelines.",
                LocalDateTime.of(2026, 4, 12, 11, 45)));
        save(new Vacancy(0, "Middle+ Java Developer",
                "Architect scalable solutions, optimize high-load systems, conduct technical interviews. Deep understanding of JVM internals and multithreading.",
                LocalDateTime.of(2026, 5, 1, 16, 0)));
        save(new Vacancy(0, "Senior Java Developer",
                "Define technical strategy, lead the development team, make architectural decisions, ensure code quality. Experience with system design and Agile methodologies.",
                LocalDateTime.of(2026, 6, 10, 13, 20)));
    }

    public static MemoryVacancyRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public void deleteById(int id) {
        vacancies.remove(id);
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(),
                (id, oldVacancy) -> new Vacancy(oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
