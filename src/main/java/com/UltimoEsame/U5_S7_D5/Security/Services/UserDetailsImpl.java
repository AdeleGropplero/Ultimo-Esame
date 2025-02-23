package com.UltimoEsame.U5_S7_D5.Security.Services;

import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {

    //Per il token jwt informazioni dell'utente.
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;

    private Collection<? extends GrantedAuthority> ruoli;

    //i ruoli vanno convertiti in granted authority (tramite il map), riconosciuto da Spring.
    public static UserDetailsImpl costruisciDettagli(Utente utente){
        System.out.println("USERDETAILS PASSWORD: " + utente.getPassword());
        System.out.println("ROLES: " + utente.getRuoli());
        List<GrantedAuthority> ruoliUtente = utente.getRuoli()
                        .stream().map(ruolo -> new SimpleGrantedAuthority(ruolo.getNomeRuolo().name()))
                        .collect(Collectors.toList());
        System.out.println("FINALROLES: " + ruoliUtente);
        System.out.println("Passwordo in userDetailsImpl: " + utente.getPassword());
        return new UserDetailsImpl(utente.getId(), utente.getUsername(),utente.getPassword(), utente.getEmail(), ruoliUtente);
    }


    @Override
    public String getPassword() {
        return password; //password
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruoli; //mi ritorno i ruoli della private Colection ruoli
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
