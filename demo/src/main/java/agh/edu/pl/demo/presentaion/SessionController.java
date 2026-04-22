package agh.edu.pl.demo.presentaion;

import agh.edu.pl.demo.model.Session;
import agh.edu.pl.demo.services.SessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public List<Session> getSessions(){
        return sessionService.getSessions();
    }
}
