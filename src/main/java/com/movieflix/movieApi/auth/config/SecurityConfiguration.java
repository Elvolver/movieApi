package com.movieflix.movieApi.auth.config;

//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

//    private final AuthFilterService authFilterService;
//    private final AuthenticationProvider authenticationProvider;
//
////    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/auth/**", "/forgotPassword/**", "/video")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(authFilterService, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
}
