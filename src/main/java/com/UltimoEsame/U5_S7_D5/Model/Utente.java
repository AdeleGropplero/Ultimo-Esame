package com.UltimoEsame.U5_S7_D5.Model;

import com.UltimoEsame.U5_S7_D5.Enum.ERuolo;
import com.UltimoEsame.U5_S7_D5.Model.Ruolo.Ruolo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "utente_ruolo",
    joinColumns = @JoinColumn(name = "utente_id"),
    inverseJoinColumns = @JoinColumn(name = "ruolo_id"))
    private Set<Ruolo> ruoli = new HashSet<>();

    public Utente(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

/*    //metodo per assegnazione ruolo di default UTENTE_GENERICO
    public void ruoloDefault(){
        if (ruoli.isEmpty()){
            Ruolo ruoloDefault = new Ruolo();
            ruoloDefault.setNomeRuolo(ERuolo.UTENTE_GENERICO);
            ruoli.add(ruoloDefault);
        }
    }*/


}
