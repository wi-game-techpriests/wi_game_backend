package agh.edu.pl.demo.services;

import agh.edu.pl.demo.model.Player;
import agh.edu.pl.demo.repos.PlayerRepository;
import agh.edu.pl.demo.util.JWTUtil;
import agh.edu.pl.demo.util.exceptions.InvalidHeaderFormatException;
import agh.edu.pl.demo.util.exceptions.MissingTokenException;
import agh.edu.pl.demo.util.exceptions.PlayerNotAuthenticatedException;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    private final PlayerRepository playerRepository;

    @Value("${auth.enabled}")
    private boolean authEnabled;


    public AuthenticationService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @PostConstruct
    private void init(){
        System.out.println("%%%% AUTHENTICATION ENABLED: "+ authEnabled + " %%%%");
    }


    public Claims authenticatePlayer(String authHeader) throws PlayerNotAuthenticatedException, MissingTokenException, InvalidHeaderFormatException {

        if(!authEnabled) return null;

        try {
            if (authHeader == null || authHeader.isBlank()) {
                throw new MissingTokenException();
            }

            if (!authHeader.startsWith("Bearer ")) {
                throw new InvalidHeaderFormatException();
            }
            String token = authHeader.substring(7);

            Claims claims = JWTUtil.parseToken(token);
            Long playerId = Long.parseLong(claims.getSubject());

            Player player = playerRepository.findById(playerId).orElseThrow();

            boolean valid =
                    Objects.equals(player.getName(), claims.get("name", String.class)) &&
                            Objects.equals(player.getSession().getId(),
                                    claims.get("sessionId", Long.class));

            if (!valid) {
                throw new PlayerNotAuthenticatedException();
            }

            return claims;
        }catch(MissingTokenException e){
            throw new MissingTokenException();
        }
        catch(InvalidHeaderFormatException e){
            throw new InvalidHeaderFormatException();
        }
        catch (Exception e) {
            throw new PlayerNotAuthenticatedException();
        }

    }
}
