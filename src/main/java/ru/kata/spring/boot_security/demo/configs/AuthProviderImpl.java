//package ru.kata.spring.boot_security.demo.configs;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class AuthProviderImpl implements AuthenticationProvider {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserDetailsService userDetailsService;
//
//    @Autowired
//    public AuthProviderImpl(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
//        this.passwordEncoder = passwordEncoder;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String user_name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(user_name);
//
//        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
//            throw new BadCredentialsException("Incorrect password!");
//        }
//
//        return new UsernamePasswordAuthenticationToken(userDetails, password,
//                userDetails.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
