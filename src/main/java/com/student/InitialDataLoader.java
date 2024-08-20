package com.student;

import com.student.entity.User;
import com.student.enums.Role;
import com.student.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.student.enums.Role.ADMIN;
import static com.student.enums.Role.USER;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup || userRepository.findAll().iterator().hasNext()) {
            return;
        }

        // Create default admin user
        createUserIfNotFound("@admin", passwordEncoder.encode("admin@"), ADMIN);

        // List of specific usernames to create
        List<String> usernames = Arrays.asList(
                "john", "dara", "alice", "bob", "charlie",
                "david", "eve", "frank", "grace", "hannah",
                "ian", "julia", "ken", "lisa", "michael",
                "nina", "oliver", "peter", "quinn", "rachel"
        );

        // Create users with USER role
        usernames.forEach(username ->
                createUserIfNotFound(username , passwordEncoder.encode(  "@123456"), USER)
        );

        alreadySetup = true;
    }

    @Transactional
    void createUserIfNotFound(String username, String password, Role role) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            user = Optional.of(new User());
            user.get().setRole(role);
            user.get().setUsername(username);
            user.get().setPassword(password);
            user.get().setAge(25);  // Adjust as necessary
            userRepository.save(user.get());
        }
    }
}
