package agh.edu.pl.demo.services;

import agh.edu.pl.demo.model.*;
import agh.edu.pl.demo.repos.*;
import agh.edu.pl.demo.util.dto.*;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
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
    private final ObjectMapper objectMapper;

    public MiniGameService(ConnectionsCategoryRepository connectionsRepository, WordSearchWordRepository wordSearchRepository, FillInEntryRepository fillInRepository, KahootQuestionRepository kahootRepository, PlayerRepository playerRepository, ObjectMapper objectMapper) {
        this.connectionsRepository = connectionsRepository;
        this.wordSearchRepository = wordSearchRepository;
        this.fillInRepository = fillInRepository;
        this.kahootRepository = kahootRepository;
        this.playerRepository = playerRepository;
        this.objectMapper = objectMapper;
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

    public List<KahootDTO> getKahoot(){

        List<KahootQuestion> kahootQuestions = kahootRepository.randomKahootQuestion();
        List<KahootDTO> kahoots = new ArrayList<>();

        for (KahootQuestion kahootQuestion:kahootQuestions) {
            List<String> answerKeys = List.of("A", "B", "C", "D");
            String correctAnswerKey = "";
            List<KahootAnswerDTO> kahootAnswers = new ArrayList<>();


            List<String> answers = new ArrayList<>(kahootQuestion.getOtherChoices());
            Collections.shuffle(answers);
            answers = answers.subList(0, 3);
            answers.add(kahootQuestion.getAnswer());
            Collections.shuffle(answers);

            for (int i = 0; i < 4; i++) {
                kahootAnswers.add(new KahootAnswerDTO(answerKeys.get(i), answers.get(i)));
                if (answers.get(i).equals(kahootQuestion.getAnswer())) correctAnswerKey = answerKeys.get(i);
            }

            kahoots.add(new KahootDTO(kahootQuestion.getQuestion(),correctAnswerKey,kahootAnswers));

        }

        return kahoots;
    }

    @Transactional
    public void submitScore(Claims claims, PlayerSubmitDTO submitDTO){
        Long playerId = Long.parseLong(claims.getSubject());

        Player player = playerRepository.findById(playerId).orElseThrow();

        player.setConnectionsPoint(submitDTO.connectionsPoints());
        player.setFillInPoints(submitDTO.fillInPoints());
        player.setWordSearchPoints(submitDTO.wordSearchPoints());
        player.setKahootPoints(submitDTO.kahootPoints());

        playerRepository.save(player);
    }

    public void reloadContent() throws IOException {
        connectionsRepository.deleteAll();
        wordSearchRepository.deleteAll();
        fillInRepository.deleteAll();
        kahootRepository.deleteAll();

        try{
            File jsonFile = new File("src/main/resources/connections.json");

            List<ConnectionsCategory> connectionsCategories =
                    objectMapper.readValue(jsonFile, new TypeReference<List<ConnectionsCategory>>(){});


            connectionsRepository.saveAll(connectionsCategories);
        } catch (Exception e){
            e.printStackTrace();
            throw new IOException();
        }

        try{
            File jsonFile = new File("src/main/resources/wordsearch.json");

            List<WordSearchWord> words =
                    objectMapper.readValue(jsonFile, new TypeReference<List<WordSearchWord>>(){});


            wordSearchRepository.saveAll(words);


        } catch (Exception e){
            e.printStackTrace();
            throw new IOException();
        }

        try{
            File jsonFile = new File("src/main/resources/fillin.json");

            List<FillInEntry> fillInEntries =
                    objectMapper.readValue(jsonFile, new TypeReference<List<FillInEntry>>(){});


            for (FillInEntry entry : fillInEntries) {
                if (entry.getFragmentEntries() != null) {
                    for (FillInAnswer answer : entry.getFragmentEntries()) {
                        answer.setParentEntry(entry);
                    }
                }
            }

            fillInRepository.saveAll(fillInEntries);


        } catch (Exception e){
            e.printStackTrace();
            throw new IOException();
        }

        try{
            File jsonFile = new File("src/main/resources/kahoot.json");

            List<KahootQuestion> questions =
                    objectMapper.readValue(jsonFile, new TypeReference<List<KahootQuestion>>(){});


            kahootRepository.saveAll(questions);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
