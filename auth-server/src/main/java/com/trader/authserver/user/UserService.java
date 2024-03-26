package com.trader.authserver.user;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, UserCreatedDto> kafkaTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    @Transactional
    RegisterResponse createUser(RegisterRequest registerRequest) {
        AppUser appUser = new AppUser(registerRequest.email(), passwordEncoder.encode(registerRequest.password()), UserRole.ROLE_REGULAR_USER);
        AppUser appUserSaved = userRepository.save(appUser);
        log.info("User with email {} has been registered with id {}", appUserSaved.getEmail(), appUserSaved.getId());
        kafkaTemplate.send("user-created", new UserCreatedDto(appUserSaved.getId(), appUserSaved.getEmail()));
        return new RegisterResponse(appUserSaved.getId(), appUserSaved.getEmail());
    }
}

record UserCreatedDto(UUID id, String email) { }

