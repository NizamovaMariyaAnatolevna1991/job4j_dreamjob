package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Иван Петров",
                "Junior Java Developer. 1 год коммерческого опыта. Знаю Java Core, Spring Boot, PostgreSQL, Git. Учился в Яндекс.Практикуме, ищу команду для развития.",
                LocalDateTime.of(2026, 5, 10, 9, 30)));
        save(new Candidate(0, "Анна Сидорова",
                "Middle Java Developer. 3 года опыта в разработке микросервисов. Spring Boot, Hibernate, Kafka, Docker, Kubernetes. Работала с highload-проектами в финтехе.",
                LocalDateTime.of(2026, 5, 18, 14, 15)));
        save(new Candidate(0, "Дмитрий Козлов",
                "Middle+ Java Developer. 4 года опыта. Глубокое понимание JVM, multithreading, оптимизация SQL. Опыт менторства джунов. Знаю Go как второй язык.",
                LocalDateTime.of(2026, 6, 1, 11, 0)));
        save(new Candidate(0, "Елена Волкова",
                "Senior Java Developer. 7+ лет опыта. Архитектура распределенных систем, system design, управление командой из 5 человек. Опыт внедрения CI/CD и code review процессов.",
                LocalDateTime.of(2026, 6, 15, 16, 45)));
    }

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public void deleteById(int id) {
        candidates.remove(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldCandidate) -> new Candidate(oldCandidate.getId(), candidate.getTitle(), candidate.getDescription(), oldCandidate.getCreationDate())) != null;

    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
