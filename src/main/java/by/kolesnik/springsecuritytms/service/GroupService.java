package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public Collection<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);

        if(group.isEmpty()) {
            throw new RuntimeException("group not found"); // todo: change with valid Exceptions
        }

        return group.get();
    }

    public Group create(Group group) {
        return groupRepository.save(group);
    }

    public Group update(Group group) {
        return groupRepository.save(group);
    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
