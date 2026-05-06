package by.kolesnik.springsecuritytms.service.db;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.repository.GroupRepository;
import by.kolesnik.springsecuritytms.service.GroupServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceDB implements GroupServiceInterface {

    private final GroupRepository groupRepository;

    @Override
    public Collection<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);

        if(group.isEmpty()) {
            throw new EntityNotFoundException("group with id=" + id + " not found");
        }

        return group.get();
    }

    @Override
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group update(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
