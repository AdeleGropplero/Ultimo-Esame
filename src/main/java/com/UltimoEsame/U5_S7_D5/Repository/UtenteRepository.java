package com.UltimoEsame.U5_S7_D5.Repository;

import com.UltimoEsame.U5_S7_D5.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    public Optional<Utente> findByUsername(String username);

    //check login
    public boolean existsByUsernameAndPassword(String username, String password);

    //check duplicate key
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
