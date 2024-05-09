package com.movieflix.movieApi.services;

import com.movieflix.movieApi.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserDto getProfileByUserName(String userName) throws UsernameNotFoundException;
}
