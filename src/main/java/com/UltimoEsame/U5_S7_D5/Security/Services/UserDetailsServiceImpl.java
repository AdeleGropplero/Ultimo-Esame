package com.UltimoEsame.U5_S7_D5.Security.Services;

import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //cerco utente tramite username
        System.out.println("searching for user: " + username);
        Utente utente = utenteRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Utente non trovato con email: " + username));
        System.out.println("✅ Utente trovato: " + utente.getUsername());
        System.out.println("🔑 Password nel DB (hashed): " + utente.getPassword());
        System.out.println(" user roles: " + utente.getRuoli());
        return UserDetailsImpl.costruisciDettagli(utente);//metodo nella classe udService passando l'utente trovado da username.
    }
}
