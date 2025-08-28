package co.com.crediya.security.jwt;

import co.com.crediya.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getName())
                .claim("roles", Stream.of("ADMIN").map(SimpleGrantedAuthority::new).toList())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration * 1000L))
                .signWith(getKey(secret))
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(getKey(secret)).build().parseSignedClaims(token).getPayload();
    }

    public String getSubject(String token) {
        return Jwts.parser().verifyWith(getKey(secret)).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().verifyWith(getKey(secret)).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (JwtException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKey getKey(String secret) {
        byte[] secretByes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(secretByes);
    }
}
