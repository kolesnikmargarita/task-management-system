package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.User;

import java.util.Collection;

public interface UserServiceInterface {

    User createUser(String username, String password, String name);

    User getCurrentUser();

    Collection<User> findAll();

    User findById(Long id);

    User findByIdAndGroupId(Long id, Group group);

    User update(User user);

    void delete(Long id);
}
