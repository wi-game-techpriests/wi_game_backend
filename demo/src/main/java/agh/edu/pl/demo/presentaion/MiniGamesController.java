package agh.edu.pl.demo.presentaion;

import agh.edu.pl.demo.model.ConnectionsCategory;
import agh.edu.pl.demo.model.FillInEntry;
import agh.edu.pl.demo.model.KahootQuestion;
import agh.edu.pl.demo.model.WordSearchWord;
import agh.edu.pl.demo.services.AuthenticationService;
import agh.edu.pl.demo.services.MiniGameService;
import agh.edu.pl.demo.util.dto.*;
import agh.edu.pl.demo.util.exceptions.InvalidHeaderFormatException;
import agh.edu.pl.demo.util.exceptions.MissingTokenException;
import agh.edu.pl.demo.util.exceptions.PlayerNotAuthenticatedException;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;


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

    @GetMapping("/kahoot")
    public List<KahootDTO> getKahoot(@RequestHeader(value = "Authorization", required = false) String authHeader){

        try{
            authenticationService.authenticatePlayer(authHeader);
            List<KahootDTO> kahoot = miniGameService.getKahoot();
            return kahoot;
        }catch (MissingTokenException | InvalidHeaderFormatException | PlayerNotAuthenticatedException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitScore(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                         @RequestBody PlayerSubmitDTO submitDTO){
        try{
            Claims claims = authenticationService.authenticatePlayer(authHeader);
            miniGameService.submitScore(claims, submitDTO);

            return ResponseEntity.ok("Score submitted");
        }catch (MissingTokenException | InvalidHeaderFormatException | PlayerNotAuthenticatedException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/reload")
    public ResponseEntity<?> relaodContent(){

        try {
            miniGameService.reloadContent();
            return ResponseEntity.ok("content reloaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @PutMapping("/add/connections")
    public ResponseEntity<?> addConnections(@RequestBody ConnectionsCategory newConnections){
        try {
            miniGameService.addConnections(newConnections);
            return ResponseEntity.ok("content added");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/add/wordsearch")
    public ResponseEntity<?> addWordSearch(@RequestBody WordSearchWord newWord){
        try {
            miniGameService.addWordSearch(newWord);
        return ResponseEntity.ok("content added");
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    }

    @PutMapping("/add/fill_in")
    public ResponseEntity<?> addFillIn(@RequestBody FillInEntry newFillIn){
        try {
            miniGameService.addFillIn(newFillIn);
            return ResponseEntity.ok("content added");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/add/kahoot")
    public ResponseEntity<?> addKahoot(@RequestBody KahootQuestion newKahoot){
        try {
            miniGameService.addKahoot(newKahoot);
            return ResponseEntity.ok("content added");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
