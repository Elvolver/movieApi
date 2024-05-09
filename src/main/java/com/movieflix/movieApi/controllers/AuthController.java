package com.movieflix.movieApi.controllers;

//@RestController
//@RequestMapping("/api/v1/auth")
public class AuthController {

//    private final AuthService authService;
//    private final RefreshTokenService refreshTokenService;
//    private final JwtService jwtService;
//
//    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
//        this.authService = authService;
//        this.refreshTokenService = refreshTokenService;
//        this.jwtService = jwtService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
//        System.out.println(registerRequest);
//        return ResponseEntity.ok(authService.register(registerRequest));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
//        return ResponseEntity.ok(authService.login(loginRequest));
//    }
//
//    @GetMapping("/user")
//    public ResponseEntity<UserDto> user() {
//        UserDto currentUser = authService.getCurrentUser();
//        if (currentUser != null) {
//            return ResponseEntity.ok(currentUser);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
//        }
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
//        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
//        User user = refreshToken.getUser();
//
//        String accessToken = jwtService.generateToken(user);
//
//        return ResponseEntity.ok(AuthResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshTokenRequest.getRefreshToken())
//                .build());
//    }
}
