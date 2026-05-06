package by.kolesnik.springsecuritytms.service.db;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.enums.Role;
import by.kolesnik.springsecuritytms.repository.UserRepository;
import by.kolesnik.springsecuritytms.service.UserServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceDB implements UserServiceInterface {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User createUser(String username, String password, String name) {
        final User user = new User();
        user.setName(name);
        user.setUsername(username);
        final String passwordHash = passwordEncoder.encode(password);
        user.setPassword(passwordHash);
        Collection<User> users = findAll();
        if(findAll().isEmpty()) {
            user.setRole(Role.ROLE_ADMIN);
        }
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("current user not found");
        }

        return optionalUser.get();
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("user with id=" + id + " not found");
        }

        return optionalUser.get();
    }

    @Override
    public User findByIdAndGroupId(Long id, Group group) {
        Optional<User> optionalUser = userRepository.findByIdAndGroups(id, group);

        if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("user with id=" + id + " not found in this group");
        }

        return optionalUser.get();
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
