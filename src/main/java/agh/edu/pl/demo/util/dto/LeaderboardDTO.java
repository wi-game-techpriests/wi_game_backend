package agh.edu.pl.demo.util.dto;

import java.util.List;

public record LeaderboardDTO(Long playerId, Long sessionId, int playerPosition, List<PlayerScoreDTO> playerScores) {
}
