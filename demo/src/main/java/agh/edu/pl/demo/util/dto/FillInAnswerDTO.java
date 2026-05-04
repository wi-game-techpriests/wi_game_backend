package agh.edu.pl.demo.util.dto;

import java.util.List;

public record FillInAnswerDTO(int answerNumber, String answer, List<String> otherChoices) {
}
