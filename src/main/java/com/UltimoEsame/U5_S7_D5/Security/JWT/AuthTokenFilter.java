package com.UltimoEsame.U5_S7_D5.Security.JWT;


import com.UltimoEsame.U5_S7_D5.Security.Services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    //Validiamo il token che ci arriva dall'header HTTP da input ServletRequest
    //Output sarà una stringa Jwt senza Bearer qual'ora ci fosse.
    private String analizzaJwt( HttpServletRequest request){
        String headAuthenticazione = request.getHeader("Authorization");

        // 1. Controllo sulla presenza di testo nel valore di Authorization
        // 2. Controllo se il valore recuperato inizia con "Bearer "
        // es. Bearer 21nsn3834d93jjz03923nflse923
        if ((StringUtils.hasText(headAuthenticazione) && (headAuthenticazione.startsWith("Bearer ")))) {
            return headAuthenticazione.substring(7);
            //stiamo chiedendo di recuperarmi la stringa dopo il settimo
            // carattere ovvero a partire da dopo lo spazio dopo Bearer.
        }
        return null;
    }

    //qui facciamo la verifica sulla nostra stringa (sottostringa) e ci facciamo i controlli sopra.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //salvo la stringa del token
        String token = analizzaJwt(request);

        //se la richiesta presenta un JWT, la convalidiamo
        if (token != null && jwtUtils.validazioneJwtToken(token)){
            //recupero lo username con il metodo fatto nella calsse JwtUtils.
            String username = jwtUtils.recuperoUsernameDaToken(token);
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // **IMPORTANTE**: permette alla richiesta di passare attraverso il filtro successivo
        filterChain.doFilter(request, response);
    }
}
