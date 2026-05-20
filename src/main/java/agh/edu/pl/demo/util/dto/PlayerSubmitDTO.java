package agh.edu.pl.demo.util.dto;

public record PlayerSubmitDTO(
                              String token,
                              int connectionsPoints,
                              int fillInPoints,
                              int wordSearchPoints,
                              int kahootPoints) {
}
