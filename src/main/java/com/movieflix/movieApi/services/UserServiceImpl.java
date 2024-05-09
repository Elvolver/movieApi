package com.movieflix.movieApi.services;

import com.movieflix.movieApi.auth.repositories.UserRepository;
import com.movieflix.movieApi.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getProfileByUserName(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        return UserDto
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(username)
                .build();
    }
}
