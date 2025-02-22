package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Payload.request.RuoloRequest;
import com.UltimoEsame.U5_S7_D5.Ruolo.ERuolo;
import com.UltimoEsame.U5_S7_D5.Ruolo.Ruolo;
import com.UltimoEsame.U5_S7_D5.Ruolo.RuoloService;
import com.UltimoEsame.U5_S7_D5.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UtenteService utenteService;

    @Autowired
    RuoloService ruoloService;

    @PostMapping("/ruoliDefault")
    public void insertRuolo() {
        Ruolo ruoloAdmin = new Ruolo(ERuolo.ROLE_ADMIN);
        ruoloService.insertRuolo(ruoloAdmin);

        Ruolo ruoloOrganizzatore = new Ruolo(ERuolo.ROLE_ORGANIZZATORE);
        ruoloService.insertRuolo(ruoloOrganizzatore);

        Ruolo ruoloUtenteGenerico = new Ruolo(ERuolo.ROLE_UTENTE);
        ruoloService.insertRuolo(ruoloUtenteGenerico);
    }

    @PostMapping("/newRuolo")
    public String insertRuolo(@RequestBody RuoloRequest ruoloRequest) {
        ERuolo eruolo = ERuolo.valueOf(ruoloRequest.getNome()); // Converte la Stringa in ERuolo
        Ruolo ruolo = new Ruolo(eruolo);
        ruoloService.insertRuolo(ruolo);
        /*
        {
          "nome": "ROLE_CUSTOM"
        }
        */
        return "Aggiunto il ruolo " + ruolo.getNomeRuolo();
    }

}

/*
{
     "nome": "ROLE_ADMIN"
}

{
     "nome": "ROLE_UTENTE"
}

{
     "nome": "ROLE_ORGANIZZATORE"
}
*/
