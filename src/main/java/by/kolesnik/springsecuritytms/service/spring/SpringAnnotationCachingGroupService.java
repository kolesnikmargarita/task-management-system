package by.kolesnik.springsecuritytms.service.spring;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.repository.GroupRepository;
import by.kolesnik.springsecuritytms.service.GroupServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringAnnotationCachingGroupService implements GroupServiceInterface {

    private final GroupRepository groupRepository;

    @Override
    public Collection<Group> findAll() {
        return groupRepository.findAll();
    }

    @Cacheable(
            value = "group",
            key = "#id"
    )
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

    @CacheEvict(
            value = "group",
            key = "#id"
    )
    @Override
    public Group update(Group group) {
        return groupRepository.save(group);
    }

    @CacheEvict(
            value = "group",
            key = "#id"
    )
    @Override
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
