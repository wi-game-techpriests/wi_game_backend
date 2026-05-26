package agh.edu.pl.demo.util.dto;

import java.util.List;

public record KahootDTO(String question, String correctAnswer, List<KahootAnswerDTO> answers) {
}
