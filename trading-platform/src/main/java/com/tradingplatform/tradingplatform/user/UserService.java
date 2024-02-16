package com.tradingplatform.tradingplatform.user;


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
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    RegisterResponse createUser(RegisterRequest registerRequest) {
        AppUser appUser = new AppUser(registerRequest.email(), registerRequest.password(), UserRole.REGULAR_USER);
        AppUser appUserSaved = userRepository.save(appUser);

        //TODO Create an account

        return new RegisterResponse(appUserSaved.getId(), appUserSaved.getEmail());
    }
}
