package by.kolesnik.springsecuritytms.service;

import by.kolesnik.springsecuritytms.entity.User;
import by.kolesnik.springsecuritytms.model.ExtendedUserDetails;
import by.kolesnik.springsecuritytms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Username '" + username + "' wos not found");
        }

        return ExtendedUserDetails.create(optionalUser.get());
    }
}
