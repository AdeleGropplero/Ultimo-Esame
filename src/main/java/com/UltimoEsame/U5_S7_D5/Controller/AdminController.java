package com.UltimoEsame.U5_S7_D5.Controller;

import com.UltimoEsame.U5_S7_D5.Ruolo.RuoloService;
import com.UltimoEsame.U5_S7_D5.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UtenteService utenteService;

    @Autowired
    RuoloService ruoloService;
}
