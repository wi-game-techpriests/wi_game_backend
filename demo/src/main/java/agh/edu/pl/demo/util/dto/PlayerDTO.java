package agh.edu.pl.demo.util.dto;

import agh.edu.pl.demo.model.Player;
import agh.edu.pl.demo.util.JWTUtil;

public record PlayerDTO(Long id,
                        Long sessionId,
                        String name,
                        String token,
                        int connectionsPoints,
                        int fillInPoints,
                        int wordSearchPoints,
                        int kahootPoints) {
    public static PlayerDTO playerToDTO(Player player) {
        return new PlayerDTO(player.getId(), player.getSession().getId(), player.getName(),
                            JWTUtil.generateToken(player), player.getConnectionsPoint(),
                            player.getFillInPoints(), player.getWordSearchPoints(),
                            player.getKahootPoints());
    }
}
