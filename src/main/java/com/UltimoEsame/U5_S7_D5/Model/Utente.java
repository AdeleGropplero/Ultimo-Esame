package com.UltimoEsame.U5_S7_D5.Model;

import com.UltimoEsame.U5_S7_D5.Ruolo.Ruolo;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "utente_ruolo",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "ruolo_id"))
    private Set<Ruolo> ruoli = new HashSet<>();


    public Utente(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

/*
  Sicuramente questo era il metodo migliore da usare in un'azienda, ma volendo fare
  come ci eravamo detti un ruolo di default iniziale che poi potessere essere cambiato ho deciso
  di usare un altro approccio più semplice. Poichè per farlo con un set e una classe Ruolo
  dovevo fare cose ben più complesse e creare una configuration e una repository anche per ruolo.
  Motivo per cui ho deciso di usare l'enum semplice.

    @ManyToMany
    @JoinTable(name = "utente_ruolo",
    joinColumns = @JoinColumn(name = "utente_id"),
    inverseJoinColumns = @JoinColumn(name = "ruolo_id"))
    private Set<Ruolo> ruoli = new HashSet<>();
*//*    @ManyToOne
    @JoinColumn(name = "ruolo_id")
    private Ruolo nomeRuolo;*/