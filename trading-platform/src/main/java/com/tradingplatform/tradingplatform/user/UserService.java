package com.tradingplatform.tradingplatform.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("User {username} not found"));
    }

    RegisterResponse createUser(RegisterRequest registerRequest) {
        AppUser appUser = new AppUser(registerRequest.email(), registerRequest.password());
        AppUser appUserSaved = userRepository.save(appUser);

        //Create account

        return new RegisterResponse(appUserSaved.getId(), appUserSaved.getEmail());
    }
}
