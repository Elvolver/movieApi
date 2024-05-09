package com.movieflix.movieApi.services;

//@Service
//@RequiredArgsConstructor
public class AuthService {

//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final JwtService jwtService;
//    private final RefreshTokenService refreshTokenService;
//    private final AuthenticationManager authenticationManager;
//    public AuthResponse register(RegisterRequest registerRequest) {
//        var user = User.builder()
//                .firstName(registerRequest.getFirstName())
//                .lastName(registerRequest.getLastName())
//                .email(registerRequest.getEmail())
//                .username(registerRequest.getUsername())
//                .password(passwordEncoder.encode(registerRequest.getPassword()))
//                .role(UserRole.USER)
//                .build();
//
//        User savedUser = userRepository.save(user);
//        var accessToken = jwtService.generateToken(savedUser);
//        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());
//
//        return AuthResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken.getRefreshToken())
//                .build();
//    }
//
//    public AuthResponse login(LoginRequest loginRequest) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
//        var accessToken = jwtService.generateToken(user);
//        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());
//
//        return AuthResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken.getRefreshToken())
//                .build();
//    }
//
//    public UserDto getCurrentUser() {
//        System.out.println("_getCurrentUser_");
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (principal instanceof User) {
//            String username = ((User)principal).getLogin();
//            String email = ((User)principal).getEmail();
//            String firstName = ((User)principal).getFirstName();
//            String lastName = ((User)principal).getLastName();
//            System.out.println(UserDto.builder()
//                    .username(username)
//                    .email(email)
//                    .firstName(firstName)
//                    .lastName(lastName)
//                    .build());
//            return UserDto.builder()
//                    .username(username)
//                    .email(email)
//                    .firstName(firstName)
//                    .lastName(lastName)
//                    .build();
//        } else {
//            System.out.println("Нихуя");
//            return null;
//        }
//
//
//    }
}
