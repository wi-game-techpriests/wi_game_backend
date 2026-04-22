package agh.edu.pl.demo.conf;

import agh.edu.pl.demo.model.Player;
import agh.edu.pl.demo.model.Session;
import agh.edu.pl.demo.repos.PlayerRepository;
import agh.edu.pl.demo.repos.SessionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
@Profile("test")
public class TestConfiguration {
    private final SessionRepository sessionRepository;
    private final PlayerRepository playerRepository;

    public TestConfiguration(SessionRepository sessionRepository, PlayerRepository playerRepository) {
        this.sessionRepository = sessionRepository;
        this.playerRepository = playerRepository;
    }

    @PostConstruct
    private void init(){
        sessionRepository.deleteAll();
        playerRepository.deleteAll();
        Session session = new Session(LocalDateTime.now(), LocalDateTime.now().plusHours(4));
        Player player = new Player(session,"kotek");
        sessionRepository.save(session);
        session.getPlayers().add(player);
        playerRepository.save(player);
        sessionRepository.save(session);

    }
}
