package ru.kata.spring.boot_security.demo.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kata.spring.boot_security.demo.services.PersonServiceImpl;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
//    private final SuccessUserHandler successUserHandler;

    private final PersonServiceImpl personService;

    @Autowired
    public WebSecurityConfig(/*SuccessUserHandler successUserHandler,*/ PersonServiceImpl personService) {
//        this.successUserHandler = successUserHandler;
        this.personService = personService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/**").permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
//        http/*.authorizeRequests()*/
////                .antMatchers("/admin/**").hasRole("ADMIN")
////                .antMatchers("/login", "/error").permitAll()
//                .antMatchers("/api/**").permitAll()
//                .anyRequest().hasAnyRole("USER", "ADMIN")
//                .and()
//                .formLogin()
//                .successHandler(successUserHandler)
//                .failureUrl("/login?error")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login");
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/**").permitAll()
//                .anyRequest().permitAll();

    }


//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(personService)
//                .passwordEncoder(passwordEncoder());
//    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}