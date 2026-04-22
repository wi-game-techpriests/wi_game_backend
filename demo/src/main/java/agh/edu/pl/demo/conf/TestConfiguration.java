package agh.edu.pl.demo.conf;

import agh.edu.pl.demo.model.Session;
import agh.edu.pl.demo.repos.SessionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
@Profile("test")
public class TestConfiguration {
    private final SessionRepository sessionRepository;

    public TestConfiguration(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @PostConstruct
    private void init(){
        sessionRepository.deleteAll();
        Session session = new Session(LocalDateTime.now(), LocalDateTime.now());
        sessionRepository.save(session);
    }
}
