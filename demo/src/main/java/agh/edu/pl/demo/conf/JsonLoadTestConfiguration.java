package agh.edu.pl.demo.conf;

import agh.edu.pl.demo.model.ConnectionsCategory;
import agh.edu.pl.demo.model.FillInAnswer;
import agh.edu.pl.demo.model.FillInEntry;
import agh.edu.pl.demo.model.WordSearchWord;
import agh.edu.pl.demo.repos.ConnectionsCategoryRepository;
import agh.edu.pl.demo.repos.FillInEntryRepository;
import agh.edu.pl.demo.repos.WordSearchWordRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;


@Configuration
@Profile("test")
public class JsonLoadTestConfiguration {

    private final ConnectionsCategoryRepository connectionsRepository;
    private final FillInEntryRepository fillInEntryRepository;
    private final WordSearchWordRepository wordSearchWordRepository;

    private final ObjectMapper objectMapper;

    public JsonLoadTestConfiguration(ConnectionsCategoryRepository connectionsRepository, FillInEntryRepository fillInEntryRepository, WordSearchWordRepository wordSearchWordRepository, ObjectMapper objectMapper) {
        this.connectionsRepository = connectionsRepository;
        this.fillInEntryRepository = fillInEntryRepository;
        this.wordSearchWordRepository = wordSearchWordRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    private void init(){
        connectionsRepository.deleteAll();
        System.out.println("Loading from example...");
        System.out.println("### LOADING CONNECTIONS ###");

        try{
            File jsonFile = new File("src/main/resources/connections_test.json");

            List<ConnectionsCategory> connectionsCategories =
                    objectMapper.readValue(jsonFile, new TypeReference<List<ConnectionsCategory>>(){});


            connectionsRepository.saveAll(connectionsCategories);


        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("### LOADING FILLIN ###");
        try{
            File jsonFile = new File("src/main/resources/fillin_test.json");

            List<FillInEntry> fillInEntries =
                    objectMapper.readValue(jsonFile, new TypeReference<List<FillInEntry>>(){});


            for (FillInEntry entry : fillInEntries) {
                if (entry.getFragmentEntries() != null) {
                    for (FillInAnswer answer : entry.getFragmentEntries()) {
                        answer.setParentEntry(entry);
                    }
                }
            }

            fillInEntryRepository.saveAll(fillInEntries);


        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("### LOADING WORDSEARCH ###");

        try{
            File jsonFile = new File("src/main/resources/wordsearch_test.json");

            List<WordSearchWord> words =
                    objectMapper.readValue(jsonFile, new TypeReference<List<WordSearchWord>>(){});


            wordSearchWordRepository.saveAll(words);


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
