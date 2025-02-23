package com.UltimoEsame.U5_S7_D5.Security.JWT;

import com.UltimoEsame.U5_S7_D5.Security.Services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpirationMs;

    // 2. creazioneJWT
    //Autentication viene generato da Spring Security nel login e contiene le info dell'utente.
    public String createJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        //Ora costruiamo il jwt
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)   //Firma token con chiave segreta.
                .compact();   //converte in stringa
    }

    //1. chiave (Key è un'interfaccia)
    public Key getKey() {
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //3.Validazione del Token JWT
    public boolean validazioneJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token); // Usa parseClaimsJws
            return true;
        } catch (ExpiredJwtException ex) {
            System.err.println("Token scaduto: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.err.println("Token non supportato: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.err.println("Token malformato: " + ex.getMessage());
        } catch (SignatureException ex) {
            System.err.println("Firma del token non valida: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.err.println("Token vuoto o nullo: " + ex.getMessage());
        }
        return false;
    }

    //4. Recupera l'username dal JWT
    public String recuperoUsernameDaToken(String token) {
        String username = Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token).getBody().getSubject(); //tramite getBody e getSubject recuperiamo lo username
        return username;
    }

    //5. recupero scadenza del jwt dal token
    public Date getExpirationFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getExpiration();
        //tramite getBody e getExpiration recuperiamo lo scadenza
    }


}
