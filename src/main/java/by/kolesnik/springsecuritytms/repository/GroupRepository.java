package by.kolesnik.springsecuritytms.repository;

import by.kolesnik.springsecuritytms.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
