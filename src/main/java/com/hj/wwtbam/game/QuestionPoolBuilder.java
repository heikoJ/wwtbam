package com.hj.wwtbam.game;

import com.hj.wwtbam.game.Answer;
import com.hj.wwtbam.game.Question;
import com.hj.wwtbam.game.QuestionPool;
import com.hj.wwtbam.util.FileUtil;

import java.io.File;
import java.util.*;

/**
 * Created by heiko on 22.08.15.
 */
public class QuestionPoolBuilder {


    private QuestionPool pool;

    public QuestionPoolBuilder() {
        pool = new QuestionPool();
    }


    public  QuestionPool buildPoolFromFile (File csvFile) throws RuntimeException {
        List<List<String>> rows = FileUtil.readFromCsvFile(csvFile);
        addQuestionsFormList(rows);
        return pool;
    }

    private  void addQuestionsFormList(List<List<String>> rows) {
        int level = 1;
        int index = 0;
        for(List<String> row : rows) {
            index++;
            String questionText = row.get(1);
            String questionText2 = row.get(2);

            List<String> answers = new ArrayList<>();

            answers.add(row.get(3));
            answers.add(row.get(5));
            answers.add(row.get(7));
            answers.add(row.get(9));

            String correctAnswerString = answers.get(0);

            Collections.shuffle(answers);

            Map<Answer,String> answerMap = new HashMap<>();


            answerMap.put(Answer.A,answers.get(0));
            answerMap.put(Answer.B,answers.get(1));
            answerMap.put(Answer.C,answers.get(2));
            answerMap.put(Answer.D,answers.get(3));

            Answer correctAnswer = answerMap.keySet().stream().
                    filter(e-> answerMap.get(e).equals(correctAnswerString)).
                    findFirst().get();

            Question question = new Question(questionText + " " + questionText2,answerMap,correctAnswer);

            pool.addQuestionForLevel(level,question);

            if(index % 100 == 0) {
                level++;
            }

        }
    }

}
