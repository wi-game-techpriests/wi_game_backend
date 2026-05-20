package agh.edu.pl.demo.util.dto;

import java.util.List;

public record FillInEntryDTO(List<String> fragments, List<FillInAnswerDTO> entries) {
}
