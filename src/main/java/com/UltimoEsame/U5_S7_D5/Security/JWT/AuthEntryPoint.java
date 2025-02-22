package com.UltimoEsame.U5_S7_D5.Security.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        //formato di ritorno al client (JSON)
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        //status della risposta
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        //Contenuto del Json di ritorno al client in caso di errore
        final Map<String, Object> informazioniErrore = new HashMap<>();
        informazioniErrore.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        informazioniErrore.put("error", "Autorizzazione non valida");
        informazioniErrore.put("message", authException.getMessage());
        informazioniErrore.put("path", request.getServletPath());

        //Mappiamo per convertire gli oggetti java in formato json.
        final ObjectMapper mappaErrori = new ObjectMapper();
        mappaErrori.writeValue(response.getOutputStream(), informazioniErrore);
        //writeValue è un metodo di ObjectMapper che serializza l'oggetto informazioniErrore in formato JSON
        // response.getOutputStream() invia il Json al client che ha fatto la richiesta.
    }
}