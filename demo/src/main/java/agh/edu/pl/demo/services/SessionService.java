package agh.edu.pl.demo.services;


import agh.edu.pl.demo.model.Session;
import agh.edu.pl.demo.repos.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Session> getSessions(){

        return sessionRepository.findAll();
    }
}
