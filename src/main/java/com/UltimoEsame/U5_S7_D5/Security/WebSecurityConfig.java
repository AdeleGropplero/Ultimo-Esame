package com.UltimoEsame.U5_S7_D5.Security;

import com.UltimoEsame.U5_S7_D5.Security.JWT.AuthEntryPoint;
import com.UltimoEsame.U5_S7_D5.Security.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthEntryPoint authEntryPoint;

    //generiamo un oggetto per criptare la psw con BCryptPasswordEncoder
    @Bean
    public PasswordEncoder pswEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Autenticazione attraverso i dettagli del nostro utente
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authentication = new DaoAuthenticationProvider();

        authentication.setUserDetailsService(userDetailsService); // importa tutti i dettagli utente da UserDetailsService

        authentication.setPasswordEncoder(pswEncoder()); //metodo per accettare pswCriptata.

        return authentication;
    }

    @Bean
    // creazione di un Bean dedicato ai filtri
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/**").permitAll() // Permetti accesso senza autenticazione a /utente/
                );

        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

/*    // Definizione del bean AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(pswEncoder())
                .build();
    }*/

}
