package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Model.Utente;
import com.UltimoEsame.U5_S7_D5.Payload.request.RuoloRequest;
import com.UltimoEsame.U5_S7_D5.Ruolo.ERuolo;
import com.UltimoEsame.U5_S7_D5.Ruolo.Ruolo;
import com.UltimoEsame.U5_S7_D5.Ruolo.RuoloService;
import com.UltimoEsame.U5_S7_D5.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UtenteService utenteService;

    @Autowired
    RuoloService ruoloService;


// Cambio Ruolo a un utente esistente. Prima di fare le operazioni dell'organizzatore
// per creare eventi è necessario che l'admin gli dia il ruolo necessario.
// L'admin può scegliere se dare o rimuovere un'autorizzazione. I metodi per farlo
// sono stati creati nel service utente.
    @PatchMapping("/{idUtente}/ruoli")
    public ResponseEntity<String> modificaRuoloUtente(@PathVariable Long idUtente,
                                                      @RequestParam Long idRuolo,
                                                      @RequestParam String operazione){
        Utente

    }


}


