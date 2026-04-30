package agh.edu.pl.demo;

import agh.edu.pl.demo.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.List;


public class JsonReadingTests {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void connectionsReadingTest(){
        String json = "  {\n" +
                "    \"category\": \"category\",\n" +
                "    \"words\": [\"aaa\",\"bbb\",\"ccc\",\"ddd\",\"eee\"]\n" +
                "  }";
        try {
            ConnectionsCategory connectionsCategory = mapper.readValue(json, ConnectionsCategory.class);
            Assertions.assertEquals(connectionsCategory.getCategory(),"category");
            Assertions.assertArrayEquals(connectionsCategory.getWords().toArray(new String[0]),
                    new String[]{"aaa","bbb","ccc","ddd","eee"});


        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void fillInEntryReadingTest(){
        String json = "{\n" +
                "    \"fragments\": [\n" +
                "      \"fragment1\",\n" +
                "      \"fragment2\",\n" +
                "      \"fragment3\"\n" +
                "    ],\n" +
                "    \"fragmentEntries\": [\n" +
                "      {\n" +
                "        \"answerNumber\": 1,\n" +
                "        \"answer\": \"ans1\",\n" +
                "        \"possibleAnswers\":\n" +
                "        [\"other1\",\"other2\"]\n" +
                "\n" +
                "      },\n" +
                "      {\n" +
                "        \"answerNumber\": 2,\n" +
                "        \"answer\": \"ans2\",\n" +
                "        \"possibleAnswers\":\n" +
                "        [\"other3\",\"other4\"]\n" +
                "\n" +
                "      }\n" +
                "    ]\n" +
                "  }";
        try{
            FillInEntry entry = mapper.readValue(json, FillInEntry.class);

            Assertions.assertArrayEquals(entry.getFragments().toArray(new String[0]),
                    new String[]{"fragment1","fragment2","fragment3"});


            List<FillInAnswer> answers = entry.getFragmentEntries();

            Assertions.assertEquals(answers.size(),2);

            Assertions.assertEquals(answers.get(0).getAnswerNumber(),1);
            Assertions.assertEquals(answers.get(1).getAnswerNumber(),2);

            Assertions.assertEquals(answers.get(0).getAnswer(),"ans1");
            Assertions.assertEquals(answers.get(1).getAnswer(),"ans2");

            Assertions.assertArrayEquals(
                    answers.get(0).getPossibleAnswers().toArray(new String[0]),
                    new String[]{"other1","other2"}
            );

            Assertions.assertArrayEquals(
                    answers.get(1).getPossibleAnswers().toArray(new String[0]),
                    new String[]{"other3","other4"}
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    public void wordSearchReadingTest(){
        String json = "  {\n" +
                "    \"word\": \"wordle\",\n" +
                "    \"wordLength\": 6\n" +
                " } ";

        try {
            WordSearchWord word = mapper.readValue(json, WordSearchWord.class);

            Assertions.assertEquals(word.getWord(),"wordle");
            Assertions.assertEquals(word.getWordLength(),6);


        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void kahootReadingTest(){
        String json = "  {\n" +
                "    \"question\": \"jedyny prawdziwy majonez?\",\n" +
                "    \"answer\": \"kielecki\",\n" +
                "    \"otherChoices\": [\n" +
                "      \"winiary\",\n" +
                "      \"hellmans\",\n" +
                "      \"heinz\",\n" +
                "      \"aioli\"\n" +
                "    ]\n" +
                "  }";

        try {
            KahootQuestion question = mapper.readValue(json, KahootQuestion.class);

            Assertions.assertEquals(question.getQuestion(), "jedyny prawdziwy majonez?");
            Assertions.assertEquals(question.getAnswer(), "kielecki");
            Assertions.assertArrayEquals(
                    question.getOtherChoices().toArray(new String[0]),
                    new String[]{"winiary","hellmans","heinz","aioli"}
            );

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
