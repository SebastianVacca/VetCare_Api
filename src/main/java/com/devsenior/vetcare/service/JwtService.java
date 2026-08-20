package com.devsenior.vetcare.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    //Convierte el texto secreto en la llave que usa jjwt para firmar
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    //Crea un token con el username, rol y fecha de vencimiento
    public String generarToken(UserDetails userDetails, String rol) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    //Verifica firma + vencimiento leyendo el contenido del token
    private Claims extraerClaims(String token) {
        return  Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Saca el username que viaja dentro del token
    public String extraerUsername(String token) {
        return extraerClaims(token).getSubject();
    }

    //El token es válido si el username coincide y no está vencido
    public boolean esValido(String token, UserDetails userDetails) {
        String username = extraerUsername(token);
        boolean vencido = extraerClaims(token).getExpiration().before(new Date());

        return username.equals(userDetails.getUsername()) && !vencido;
    }
}
