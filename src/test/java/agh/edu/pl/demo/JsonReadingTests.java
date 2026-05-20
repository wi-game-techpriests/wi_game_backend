package agh.edu.pl.demo;

import agh.edu.pl.demo.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;


class JsonReadingTests {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void connectionsReadingTest(){
        String json = """
                  {
                    "category": "category",
                    "words": ["aaa","bbb","ccc","ddd","eee"]
                  }
                """;
        try {
            ConnectionsCategory connectionsCategory = mapper.readValue(json, ConnectionsCategory.class);
            Assertions.assertEquals("category", connectionsCategory.getCategory());
            Assertions.assertArrayEquals(new String[]{"aaa","bbb","ccc","ddd","eee"},
                    connectionsCategory.getWords().toArray(new String[0]));


        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void fillInEntryReadingTest(){
        String json = """
        {
          "fragments": [
            "fragment1",
            "fragment2",
            "fragment3"
          ],
          "fragmentEntries": [
            {
              "answerNumber": 1,
              "answer": "ans1",
              "possibleAnswers": ["other1","other2"]
            },
            {
              "answerNumber": 2,
              "answer": "ans2",
              "possibleAnswers": ["other3","other4"]
            }
          ]
        }
        """;

        try {
            FillInEntry entry = mapper.readValue(json, FillInEntry.class);

            Assertions.assertArrayEquals(new String[]{"fragment1", "fragment2", "fragment3"},
                    entry.getFragments().toArray(new String[0]));


            List<FillInAnswer> answers = entry.getFragmentEntries();

            Assertions.assertEquals(2, answers.size());

            Assertions.assertEquals(1, answers.get(0).getAnswerNumber());
            Assertions.assertEquals(2, answers.get(1).getAnswerNumber());

            Assertions.assertEquals("ans1", answers.get(0).getAnswer());
            Assertions.assertEquals("ans2", answers.get(1).getAnswer());

            Assertions.assertArrayEquals(
                    new String[]{"other1","other2"},
                    answers.get(0).getPossibleAnswers().toArray(new String[0])
            );

            Assertions.assertArrayEquals(
                    new String[]{"other3","other4"},
                    answers.get(1).getPossibleAnswers().toArray(new String[0])
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    void wordSearchReadingTest(){
        String json = """
        {
          "word": "wordle",
          "wordLength": 6
        }
        """;

        try {
            WordSearchWord word = mapper.readValue(json, WordSearchWord.class);

            Assertions.assertEquals("wordle", word.getWord());
            Assertions.assertEquals(6, word.getWordLength());


        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void kahootReadingTest(){
        String json = """
        {
          "question": "jedyny prawdziwy majonez?",
          "answer": "kielecki",
          "otherChoices": [
            "winiary",
            "hellmans",
            "heinz",
            "aioli"
          ]
        }
        """;

        try {
            KahootQuestion question = mapper.readValue(json, KahootQuestion.class);

            Assertions.assertEquals("jedyny prawdziwy majonez?", question.getQuestion());
            Assertions.assertEquals("kielecki", question.getAnswer());
            Assertions.assertArrayEquals(
                    new String[]{"winiary", "hellmans", "heinz", "aioli"},
                    question.getOtherChoices().toArray(new String[0])
            );

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
