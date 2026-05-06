package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.entity.Group;

import java.util.Collection;

public interface GroupServiceInterface {

    Collection<Group> findAll();

    Group findById(Long id);

    Group create(Group group);

    Group update(Group group);

    void delete(Long id);
}
