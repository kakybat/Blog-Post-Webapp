package com.kakybat.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
//    private final UserDetailsService userDetailsService;
//    @Autowired
//    public WebSecurityConfig(UserDetailsService userDetailsService){
//        this.userDetailsService = userDetailsService;
//    }
//    @Bean
//    public static PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(AbstractHttpConfigurer::disable)
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/saveMessage").ignoringRequestMatchers("/public/**"))
                .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/", "/home", "/blog", "/contact", "/about", "/postHeaderImage/**", "/userImage/**", "/saveMessage", "/public/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/css/**", "/js/**", "/images/**", "/fonts/**","/posts/*", "/comments/save").permitAll()
                    .requestMatchers("/displayMessages").hasRole("ADMIN")
                    .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                    .anyRequest().authenticated())
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/dashboard")
                       .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                                .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                                .permitAll())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

