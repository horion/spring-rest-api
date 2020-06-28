package br.com.alura.forum.config.security;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private long expiration;


    @Value("${forum.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authenticate){
        Usuario usuarioLogado = (Usuario) authenticate.getPrincipal();
        Date now = new Date();
        long expirationLong = now.getTime() + expiration;
        Date expirationDate = new Date(expirationLong);
        return Jwts.builder().setIssuer("Api do Forum Alura")
                .setSubject(String.valueOf(usuarioLogado.getId()))
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public boolean isValidToken(String token) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Long getIdUserByToken(String token) {
        try{
            Claims claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return Long.valueOf(claimsJws.getSubject());
        }catch (Exception e){
            return null;
        }
    }
}
