package agh.edu.pl.demo.util.dto;

import agh.edu.pl.demo.model.Player;
import agh.edu.pl.demo.model.Session;

import java.time.LocalDateTime;
import java.util.HashSet;

public record SessionDTO(Long id,
                         String name,
                         String joinCode,
                         LocalDateTime startTime,
                         LocalDateTime endTime,
                         HashSet<PlayerDTO> players) {
    public static SessionDTO sessionToDTO(Session session) {

        HashSet<PlayerDTO> playersDTOs = new HashSet<>();
        for (Player player : session.getPlayers()) {
            playersDTOs.add(PlayerDTO.playerToDTO(player));
        }

        return new SessionDTO(session.getId(), session.getName(), session.getJoinCode(),
                                session.getStartTime(), session.getEndTime(), playersDTOs);
    }
}
