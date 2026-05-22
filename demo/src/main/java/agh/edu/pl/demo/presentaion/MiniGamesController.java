package agh.edu.pl.demo.presentaion;

import agh.edu.pl.demo.services.AuthenticationService;
import agh.edu.pl.demo.services.MiniGameService;
import agh.edu.pl.demo.util.dto.ConnectionsDTO;
import agh.edu.pl.demo.util.dto.FillInEntryDTO;
import agh.edu.pl.demo.util.dto.WordSearchDTO;
import agh.edu.pl.demo.util.exceptions.InvalidHeaderFormatException;
import agh.edu.pl.demo.util.exceptions.MissingTokenException;
import agh.edu.pl.demo.util.exceptions.PlayerNotAuthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(path = "game")
public class MiniGamesController {

    private final MiniGameService miniGameService;
    private final AuthenticationService authenticationService;

    public MiniGamesController(MiniGameService miniGameService, AuthenticationService authenticationService) {
        this.miniGameService = miniGameService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/connections")
    public ConnectionsDTO getConnections(@RequestHeader(value = "Authorization", required = false) String authHeader){

        try{
            authenticationService.authenticatePlayer(authHeader);
            ConnectionsDTO connectionsDTO = miniGameService.getConnections(); // like that for the purpose of future error handling
            return connectionsDTO;
        }catch (MissingTokenException | InvalidHeaderFormatException | PlayerNotAuthenticatedException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }


    @GetMapping("/wordsearch")
    public WordSearchDTO getWordSearch(@RequestHeader(value = "Authorization", required = false) String authHeader){

        try{
            authenticationService.authenticatePlayer(authHeader);
            WordSearchDTO words = miniGameService.getWordSearch();
            return words;
        }catch (MissingTokenException | InvalidHeaderFormatException | PlayerNotAuthenticatedException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }


    }

    @GetMapping("/fill_in")
    public FillInEntryDTO getFillIn(@RequestHeader(value = "Authorization", required = false) String authHeader){

        try{
            authenticationService.authenticatePlayer(authHeader);
            FillInEntryDTO fillInEntry = miniGameService.getFillIn();
            return fillInEntry;
        }catch (MissingTokenException | InvalidHeaderFormatException | PlayerNotAuthenticatedException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }
}
