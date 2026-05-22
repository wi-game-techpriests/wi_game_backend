package agh.edu.pl.demo.presentaion;


import agh.edu.pl.demo.model.Session;
import agh.edu.pl.demo.services.AuthenticationService;
import agh.edu.pl.demo.services.SessionService;
import agh.edu.pl.demo.util.GameType;
import agh.edu.pl.demo.util.dto.LeaderboardDTO;
import agh.edu.pl.demo.util.dto.PlayerDTO;
import agh.edu.pl.demo.util.dto.SessionDTO;
import agh.edu.pl.demo.util.exceptions.PlayerAlreadyExistsException;
import agh.edu.pl.demo.util.exceptions.SessionExpiredException;
import agh.edu.pl.demo.util.exceptions.SessionNotFoundException;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

@RestController
@RequestMapping(path = "sessions")
public class SessionController {

    private final SessionService sessionService;
    private final AuthenticationService authenticationService;

    public SessionController(SessionService sessionService, AuthenticationService authenticationService) {
        this.sessionService = sessionService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public List<SessionDTO> getSessions() {
        List<SessionDTO> result = new ArrayList<>();

        for (Session session : sessionService.getSessions()) {
            result.add(SessionDTO.sessionToDTO(session));
        }

        return result;
    }

    @PostMapping
    public SessionDTO createSession(@RequestParam String name, @RequestParam String endTime) {
        Session newSession = this.sessionService.createSession(name, LocalDateTime.parse(endTime));

        return SessionDTO.sessionToDTO(newSession);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinSession(@RequestParam String code, @RequestParam String nick) {
        try {
            return ResponseEntity.ok(PlayerDTO.playerToDTO(this.sessionService.joinSession(code, nick)));
        } catch (SessionNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body("Session not found");
        } catch (PlayerAlreadyExistsException e) {
            return ResponseEntity
                    .status(403)
                    .body("Player with this name already exists in session");
        } catch (SessionExpiredException e) {
            return ResponseEntity
                    .status(404)
                    .body("Session expired");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSession(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(SessionDTO.sessionToDTO(this.sessionService.deleteSession(id)));
        } catch (SessionNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body("Session not found");
        }
    }


    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                            @RequestParam GameType gameType){
        try{
            Claims claims = authenticationService.authenticatePlayer(authHeader);

            LeaderboardDTO leaderboard = sessionService.getLeaderBoard(claims,gameType);

            return ResponseEntity.ok(leaderboard);
        }catch (Exception e){
            return ResponseEntity.status(404).body("//TODO");
        }
    }
}
