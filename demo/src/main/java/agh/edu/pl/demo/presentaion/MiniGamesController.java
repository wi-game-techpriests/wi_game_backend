package agh.edu.pl.demo.presentaion;

import agh.edu.pl.demo.services.MiniGameService;
import agh.edu.pl.demo.util.dto.ConnectionsDTO;
import agh.edu.pl.demo.util.dto.FillInEntryDTO;
import agh.edu.pl.demo.util.dto.WordSearchDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "game")
public class MiniGamesController {

    private final MiniGameService miniGameService;

    public MiniGamesController(MiniGameService miniGameService) {
        this.miniGameService = miniGameService;
    }

    @GetMapping("/connections")
    public ConnectionsDTO getConnections(){
        ConnectionsDTO connectionsDTO = miniGameService.getConnections(); // like that for the purpose of future error handling
        return connectionsDTO;
    }


    @GetMapping("/wordsearch")
    public WordSearchDTO getWordSearch(){
        WordSearchDTO words = miniGameService.getWordSearch();
        return words;
    }

    @GetMapping("/fill_in")
    public FillInEntryDTO getFillIn(){
        FillInEntryDTO fillInEntry = miniGameService.getFillIn();

        return fillInEntry;
    }
}
