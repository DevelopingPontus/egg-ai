// package chasky.ai_gatherer.common.security;

// import java.util.Arrays;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// @Configuration
// @EnableWebSecurity
// public class WebSecurityConfig {

//     @Bean
//     public UserDetailsService userDetailsService() {
//         PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//         // outputs {bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
//         System.out.println(encoder.encode("password"));

//         UserDetails user = User.withUsername("user")
//                 .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
//                 .roles("USER")
//                 .build();

//         InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//         manager.createUser(user);
//         return manager;
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration config = new CorsConfiguration();
//         config.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
//         config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//         config.setAllowCredentials(true);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", config);
//         return source;
//     }

//     @Bean
//     @Order(1)
//     public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
//         // http
//         http.csrf(csrf -> csrf.disable()) // only for testing!
//                 .cors(Customizer.withDefaults()) // Uses the bean above
//                 .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
//                 .securityMatcher("/api/**", "/h2-console/**")
//                 .authorizeHttpRequests((authorize) -> authorize
//                         .anyRequest().hasRole("USER"))
//                 .httpBasic(Customizer.withDefaults());
//         return http.build();
//     }

//     @Bean
//     public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .authorizeHttpRequests((authorize) -> authorize
//                         .anyRequest().authenticated())
//                 .formLogin(Customizer.withDefaults());
//         return http.build();
//     }

// }
