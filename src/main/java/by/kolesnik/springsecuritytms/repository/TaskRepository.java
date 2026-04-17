package by.kolesnik.springsecuritytms.repository;

import by.kolesnik.springsecuritytms.entity.Task;
import by.kolesnik.springsecuritytms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Collection<Task> findAllByCreator(User user);

    Optional<Task> findByIdAndCreator(Long id, User user);
}
