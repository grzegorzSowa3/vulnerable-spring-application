package pl.recompiled.vulnerablespringapplicationdemo.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.recompiled.vulnerablespringapplicationdemo.mail.MailService;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final Random random = new Random();

    public String createUser(CreateUserDto dto) {
        final String token = RandomStringUtils.random(48, 0, 0, true, true, null, this.random);
        final User user = User.newInstance(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                Set.of(Authority.USER),
                token);
        final User savedUser = userRepository.save(user);
        mailService.sendActivationMail(token);
        return savedUser.getId().toString();
    }

    public void activateUser(String token) {
        Optional<User> result = userRepository.findByActivationToken(token);
        if (result.isPresent()) {
            User user = result.get();
            user.activate();
            log.info("user " + user.getUsername() + " activated");
            userRepository.save(user);
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with such username!"));
    }
}
