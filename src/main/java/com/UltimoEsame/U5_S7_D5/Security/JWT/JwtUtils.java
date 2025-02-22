package com.UltimoEsame.U5_S7_D5.Security.JWT;

import com.UltimoEsame.U5_S7_D5.Security.Services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private String jwtExpiration;

    // 2. creazioneJWT
    //Autentication viene generato da Spring Security nel login e contiene le info dell'utente.
    public String createJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();

        //Ora costruiamo il jwt
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(getKey(), SignatureAlgorithm.ES256)   //Firma token con chiave segreta.
                .compact();   //converte in stringa
    }

    //1. chiave (Key è un'interfaccia)
    public Key getKey() {
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //3.Validazione del Token JWT
    public boolean validazioneJwtToken(String token) {
        //facciamo il parse e poi inseriamo la firma, recuperiamo la chiave e
        // mettiamo due metodi ad incastro .build .parse.
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //4. Recupera l'username dal JWT
    public String recuperoUsernameDaToken(String token) {
        String username = Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJwt(token).getBody().getSubject(); //tramite getBody e getSubject recuperiamo lo username
        return username;
    }

    //5. recupero scadenza del jwt dal token
    public Date getExpirationFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJwt(token).getBody().getExpiration();
        //tramite getBody e getExpiration recuperiamo lo scadenza
    }


}
