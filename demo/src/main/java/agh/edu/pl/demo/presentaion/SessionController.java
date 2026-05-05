package agh.edu.pl.demo.presentaion;

import agh.edu.pl.demo.model.Player;
import agh.edu.pl.demo.model.Session;
import agh.edu.pl.demo.services.SessionService;
import agh.edu.pl.demo.util.dto.PlayerDTO;
import agh.edu.pl.demo.util.dto.SessionDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(path = "sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
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
    public PlayerDTO joinSession(@RequestParam String code, @RequestParam String nick) {
        return PlayerDTO.playerToDTO(this.sessionService.joinSession(code, nick));
    }

    @DeleteMapping
    public SessionDTO deleteSession(@RequestParam Long id) {
        return SessionDTO.sessionToDTO(this.sessionService.deleteSession(id));
    }
}
