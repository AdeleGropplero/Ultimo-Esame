package com.UltimoEsame.U5_S7_D5.Security;

import com.UltimoEsame.U5_S7_D5.Security.JWT.AuthEntryPoint;
import com.UltimoEsame.U5_S7_D5.Security.JWT.AuthTokenFilter;
import com.UltimoEsame.U5_S7_D5.Security.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPoint authEntryPoint;


    // Genera un oggetto per criptare la psw con BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Autenticazione attraverso i dettagli del nostro utente
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authentication = new DaoAuthenticationProvider();
        authentication.setUserDetailsService(userDetailsService); // importa tutti i dettagli utente da UserDetailsService
        authentication.setPasswordEncoder(passwordEncoder()); // metodo per accettare psw criptata
        return authentication;
    }

    // Espone il bean AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Creazione di un Bean dedicato ai filtri
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //nota mancano i cors, vedi dopo se serve inserirli o meno
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()// Endpoint pubblici (registrazione/login)
                        .requestMatchers("/utente/**").permitAll()  // Endpoint utente accessibile senza autenticazione
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/organizzatore/**").hasAuthority("ROLE_ORGANIZZATORE") // Solo organizzatori possono gestire eventi
                        .requestMatchers("/prenotazioni/**").hasAuthority("ROLE_USER")// Solo utenti possono prenotare
                        .anyRequest().authenticated());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);//mancava
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }



}
