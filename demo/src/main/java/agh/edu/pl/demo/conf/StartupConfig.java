package agh.edu.pl.demo.conf;

import agh.edu.pl.demo.model.Player;
import agh.edu.pl.demo.model.Session;
import agh.edu.pl.demo.repos.ConnectionsCategoryRepository;
import agh.edu.pl.demo.repos.PlayerRepository;
import agh.edu.pl.demo.repos.SessionRepository;
import agh.edu.pl.demo.services.MiniGameService;
import jakarta.annotation.PostConstruct;
import jdk.internal.org.jline.reader.History;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class StartupConfig {

    private final SessionRepository sessionRepository;
    private final ConnectionsCategoryRepository connectionsRepository;
    private final MiniGameService miniGameService;
    private final PlayerRepository playerRepository;

    public StartupConfig(SessionRepository sessionRepository, ConnectionsCategoryRepository connectionsRepository, MiniGameService miniGameService, PlayerRepository playerRepository) {
        this.sessionRepository = sessionRepository;
        this.connectionsRepository = connectionsRepository;
        this.miniGameService = miniGameService;
        this.playerRepository = playerRepository;
    }


    @PostConstruct
    private void init(){

        if(sessionRepository.findAll().isEmpty()) {

            Session session = new Session("Testowa Sesja", "ABCDEF", LocalDateTime.now(), LocalDateTime.now().plusYears(69));
            sessionRepository.save(session);
            Player player = new Player(session, "kotek");

            sessionRepository.save(session);
            session.getPlayers().add(player);
            playerRepository.save(player);
            sessionRepository.save(session);
        }
        try {

            if (connectionsRepository.findAll().isEmpty()) miniGameService.reloadContent();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
