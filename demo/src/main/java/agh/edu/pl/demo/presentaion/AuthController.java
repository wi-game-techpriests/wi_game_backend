package agh.edu.pl.demo.presentaion;

import agh.edu.pl.demo.services.AuthenticationService;
import agh.edu.pl.demo.util.exceptions.InvalidHeaderFormatException;
import agh.edu.pl.demo.util.exceptions.MissingTokenException;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "auth")
public class AuthController {
    private final AuthenticationService authenticationService;


    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }



    @GetMapping("/parse")
    public ResponseEntity<?> parseToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ){

        try {
            Claims claims = authenticationService.authenticatePlayer(authHeader);

            if(claims == null) return ResponseEntity.ok("auth disabled");

            Map<String, Object> response = new HashMap<>();
            response.put("playerId", claims.getSubject());
            response.put("name", claims.get("name", String.class));
            response.put("sessionId", claims.get("sessionId", Long.class));

            return ResponseEntity.ok(response);

        }catch(MissingTokenException e){
            return ResponseEntity
                    .status(401)
                    .body("Missing Authorization header");
        }
        catch(InvalidHeaderFormatException e){
            return ResponseEntity
                    .status(401)
                    .body("Invalid Authorization header format. Expected 'Bearer <token>'");
        } catch (Exception e) {
            return ResponseEntity
                    .status(498)
                    .body("Invalid or expired token");
        }
    }
}
