package by.kolesnik.springsecuritytms.repository;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndGroups(Long id, Group group);
}
