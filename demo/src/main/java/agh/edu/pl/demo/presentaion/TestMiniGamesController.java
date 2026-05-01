package agh.edu.pl.demo.presentaion;

import agh.edu.pl.demo.util.dto.*;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "test")
@Profile("test")
public class TestMiniGamesController {

    private ConnectionsDTO connectionsDTO;
    private WordSearchDTO wordSearchDTO;
    private FillInEntryDTO fillInEntryDTO;

    @PostConstruct
    private void initConnections(){
        //connections
        connectionsDTO = new ConnectionsDTO(
                new CategoryDTO(
                "a",
                Arrays.asList("a1","a2","a3","a4")),
                new CategoryDTO(
                "b",
                Arrays.asList("b1","b2","b3","b4")),
                        new CategoryDTO(
                "c",
                Arrays.asList("c1","c2","c3","c4")),
                                new CategoryDTO(
                "d",
                Arrays.asList("d1","d2","d3","d4"))
        );
    }

    @PostConstruct
    private void initWordsearch(){
        wordSearchDTO = new WordSearchDTO(
                Arrays.asList("java","haskell","nullptr","gaelrnik","printf")
        );
    }

    @PostConstruct
    private void initFillIn(){
        fillInEntryDTO = new FillInEntryDTO(
                Arrays.asList("fragment1", "fragment2", "fragment3", "fragment4"),
                Arrays.asList(
                        new FillInAnswerDTO(1,"ans1",
                                Arrays.asList("oth1","oth2","oth3")),
                        new FillInAnswerDTO(2,"ans2",
                                Arrays.asList("oth4","oth5","oth6")),
                        new FillInAnswerDTO(3,"ans3",
                                Arrays.asList("oth7","oth8","oth9"))
                )
        );
    }

    @GetMapping("/connections")
    public ConnectionsDTO getConnections(){
        return connectionsDTO;
    }

    @GetMapping("/wordsearch")
    public WordSearchDTO getWordSearch(){
        return wordSearchDTO;
    }

    @GetMapping("/fill_in")
    public FillInEntryDTO getFillIn(){
        return fillInEntryDTO;
    }

}
