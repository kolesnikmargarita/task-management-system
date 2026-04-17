package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.entity.Group;
import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.enums.Role;
import by.kolesnik.springsecuritytms.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User createUser(String username, String password, String name) {
        final User user = new User();
        user.setName(name);
        user.setUsername(username);
        final String passwordHash = passwordEncoder.encode(password);
        user.setPassword(passwordHash);
        if(findAll().isEmpty()) {
            user.setRole(Role.ROLE_ADMIN);
        }
        return userRepository.save(user);
    }

    public User getCurrentUser() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("current user not found");
        }

        return optionalUser.get();
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("user with id=" + id + " not found");
        }

        return optionalUser.get();
    }

    public User findByIdAndGroupId(Long id, Group group) {
        Optional<User> optionalUser = userRepository.findByIdAndGroups(id, group);

        if(optionalUser.isEmpty()) {
            throw new EntityNotFoundException("user with id=" + id + " not found in this group");
        }

        return optionalUser.get();
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
