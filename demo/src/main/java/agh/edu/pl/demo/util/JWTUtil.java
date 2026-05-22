package agh.edu.pl.demo.util;

import agh.edu.pl.demo.model.Player;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${auth.enabled:false}")
    private static boolean authEnabled;

    @Value("${auth.secret:secret}")
    private static String secret="secret01secret02secret03secret04";

    private JWTUtil() {
        throw new IllegalStateException("Utility class");
    }


    public static String generateToken(Player player){
        return Jwts.builder()
                .setSubject(player.getId().toString())
                .claim("name",player.getName())
                .claim("sessionId", player.getSession().getId())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(
                        player.getSession().getEndTime()
                                .atZone(ZoneId.systemDefault()).toInstant()
                ))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }


    public static Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();


    }

}
