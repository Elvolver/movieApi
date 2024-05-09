package com.movieflix.movieApi.auth.utils;

public record ChangePassword(String email, Integer otp, String newPassword, String confirmPassword) {

}
