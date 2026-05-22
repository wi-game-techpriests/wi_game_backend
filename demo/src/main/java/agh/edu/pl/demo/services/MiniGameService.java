package agh.edu.pl.demo.services;

import agh.edu.pl.demo.model.*;
import agh.edu.pl.demo.repos.*;
import agh.edu.pl.demo.util.dto.*;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MiniGameService {
    private final ConnectionsCategoryRepository connectionsRepository;
    private final WordSearchWordRepository wordSearchRepository;
    private final FillInEntryRepository fillInRepository;
    private final KahootQuestionRepository kahootRepository;

    private final PlayerRepository playerRepository;

    public MiniGameService(ConnectionsCategoryRepository connectionsRepository, WordSearchWordRepository wordSearchRepository, FillInEntryRepository fillInRepository, KahootQuestionRepository kahootRepository, PlayerRepository playerRepository) {
        this.connectionsRepository = connectionsRepository;
        this.wordSearchRepository = wordSearchRepository;
        this.fillInRepository = fillInRepository;
        this.kahootRepository = kahootRepository;
        this.playerRepository = playerRepository;
    }


    public ConnectionsDTO getConnections(){
        List<ConnectionsCategory> categories = connectionsRepository.findRandomCategories();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        for(ConnectionsCategory c: categories){
            List<String> categoryWords = new ArrayList<>(c.getWords());
            if(categoryWords.size() == 4){
                categoryDTOs.add(
                        new CategoryDTO(c.getCategory(),c.getWords())
                );
            } else {
                //shuffle magic
                Collections.shuffle(categoryWords);
                categoryDTOs.add(
                        new CategoryDTO(c.getCategory(),categoryWords.subList(0,4))
                );
            }
        }

        return new ConnectionsDTO(
                categoryDTOs.get(0),categoryDTOs.get(1),
                categoryDTOs.get(2),categoryDTOs.get(3)
                );

    }

    public WordSearchDTO getWordSearch(){

        List<String> words = wordSearchRepository.findRandomWords()
                .stream()
                .map(WordSearchWord::getWord)
                .toList();
        return new WordSearchDTO(words);
    }


    public FillInEntryDTO getFillIn(){
        FillInEntry fillInEntry = fillInRepository.findRandomFillInEntry();

        List<FillInAnswer> fillInAnswers = fillInEntry.getFragmentEntries();

        List<FillInAnswerDTO> answers = new ArrayList<>();
        for(FillInAnswer a: fillInAnswers){
            if(a.getPossibleAnswers().size()<=3){
                FillInAnswerDTO answerDTO = new FillInAnswerDTO(a.getAnswerNumber(), a.getAnswer(),a.getPossibleAnswers());
                answers.add(answerDTO);
            } else {
                //shuffle magic
                List<String> possibleAnswers = new ArrayList<>(a.getPossibleAnswers());
                Collections.shuffle(possibleAnswers);
                FillInAnswerDTO answerDTO = new FillInAnswerDTO(a.getAnswerNumber(), a.getAnswer(),
                        possibleAnswers.subList(0,3));
                answers.add(answerDTO);
            }
        }


        return new FillInEntryDTO(fillInEntry.getFragments(),answers);
    }

    public KahootDTO getKahoot(){
        List<String> answerKeys = List.of("A","B","C","D");
        String correctAnswerKey = "";
        List<KahootAnswerDTO> kahootAnswers = new ArrayList<>();
        KahootQuestion kahootQuestion = kahootRepository.randomKahootQuestion();

        List<String> answers = new ArrayList<>(kahootQuestion.getOtherChoices());
        Collections.shuffle(answers);
        answers = answers.subList(0,3);
        answers.add(kahootQuestion.getAnswer());
        Collections.shuffle(answers);

        for(int i = 0; i<4;i++){
            kahootAnswers.add(new KahootAnswerDTO(answerKeys.get(i),answers.get(i)));
            if(answers.get(i).equals(kahootQuestion.getAnswer())) correctAnswerKey = answerKeys.get(i);
        }

        return new KahootDTO(kahootQuestion.getQuestion(),correctAnswerKey,kahootAnswers);
    }

    public void submitScore(Claims claims, PlayerSubmitDTO submitDTO){
        Long playerId = Long.parseLong(claims.getSubject());

        Player player = playerRepository.findById(playerId).orElseThrow();

        player.setConnectionsPoint(submitDTO.connectionsPoints());
        player.setFillInPoints(submitDTO.fillInPoints());
        player.setWordSearchPoints(submitDTO.wordSearchPoints());
        player.setKahootPoints(submitDTO.kahootPoints());

        playerRepository.save(player);
    }

}
