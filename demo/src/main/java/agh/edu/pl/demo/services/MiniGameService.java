package agh.edu.pl.demo.services;

import agh.edu.pl.demo.model.ConnectionsCategory;
import agh.edu.pl.demo.model.FillInAnswer;
import agh.edu.pl.demo.model.FillInEntry;
import agh.edu.pl.demo.model.WordSearchWord;
import agh.edu.pl.demo.repos.ConnectionsCategoryRepository;
import agh.edu.pl.demo.repos.FillInEntryRepository;
import agh.edu.pl.demo.repos.WordSearchWordRepository;
import agh.edu.pl.demo.util.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class MiniGameService {
    private final ConnectionsCategoryRepository connectionsRepository;
    private final WordSearchWordRepository wordSearchRepository;
    private final FillInEntryRepository fillInRepository;

    public MiniGameService(ConnectionsCategoryRepository connectionsRepository, WordSearchWordRepository wordSearchRepository, FillInEntryRepository fillInRepository) {
        this.connectionsRepository = connectionsRepository;
        this.wordSearchRepository = wordSearchRepository;
        this.fillInRepository = fillInRepository;
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

}
