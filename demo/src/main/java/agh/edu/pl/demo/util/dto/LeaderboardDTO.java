package agh.edu.pl.demo.util.dto;

import java.util.List;

public record LeaderboardDTO(Long playerId, String playerName, int playerPosition, List<PlayerScoreDTO> scores) {
}
