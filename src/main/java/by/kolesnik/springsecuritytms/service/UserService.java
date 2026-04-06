package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    //@Transactional
    public User createUser(String username, String password) {
        final User user = new User();
        user.setUsername(username);
        final String passwordHash = passwordEncoder.encode(password);
        user.setPassword(passwordHash);
        return userRepository.save(user);
    }
}
