package agh.edu.pl.demo.services;


import agh.edu.pl.demo.model.Player;
import agh.edu.pl.demo.model.Session;
import agh.edu.pl.demo.repos.PlayerRepository;
import agh.edu.pl.demo.repos.SessionRepository;
import agh.edu.pl.demo.util.exceptions.PlayerAlreadyExistsException;
import agh.edu.pl.demo.util.exceptions.SessionExpiredException;
import agh.edu.pl.demo.util.exceptions.SessionNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final PlayerRepository playerRepository;

    public SessionService(SessionRepository sessionRepository, PlayerRepository playerRepository) {
        this.sessionRepository = sessionRepository;
        this.playerRepository = playerRepository;
    }

    public Session createSession(String name, LocalDateTime endTime) {
        Session session = new Session();

        session.setName(name);
        session.setStartTime(LocalDateTime.now());
        session.setEndTime(endTime);
        session.setJoinCode(generateRandomCode());

        return this.sessionRepository.save(session);
    }

    public Player joinSession(String code, String nickname) throws SessionNotFoundException, PlayerAlreadyExistsException, SessionExpiredException {
        Session session = sessionRepository.findByJoinCode(code)
                        .orElseThrow(SessionNotFoundException::new);

        if (session.getEndTime().isBefore(LocalDateTime.now())) {
            throw new SessionExpiredException();
        }

        for (Player p : session.getPlayers()) {
            if (p.getName().equals(nickname)) {
                throw new PlayerAlreadyExistsException();
            }
        }

        Player player = new Player(session, nickname, UUID.randomUUID().toString());

        return this.playerRepository.save(player);
    }

    public List<Session> getSessions() {
        return sessionRepository.findAll();
    }

    public Session deleteSession(Long id) throws SessionNotFoundException {
        Session session = sessionRepository.findById(id)
                .orElseThrow(SessionNotFoundException::new);

        sessionRepository.delete(session);

        return session;
    }

    private String generateRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }
}
