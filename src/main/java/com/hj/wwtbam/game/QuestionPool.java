package com.hj.wwtbam.game;

import com.hj.wwtbam.util.FileUtil;
import com.sun.tools.javac.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by heiko on 19.08.15.
 */
public class QuestionPool {

    private Map<Integer,List<Question>> questions;

    QuestionPool() {
        questions = new HashMap<>();
    }

    void addQuestionForLevel(Integer level,Question question) {
        List<Question> questionsForLevel = questions.get(level);
        if(questionsForLevel==null) {
            questionsForLevel = new ArrayList<>();
            questions.put(level,questionsForLevel);
        }
        questionsForLevel.add(question);
    }

     Question getRandomQuestionForLevel(Integer level) {
        List<Question> questionsForLevel = questions.get(level);
        Assert.checkNonNull(questionsForLevel,"QuestionPool was not initialized!");
        if(questionsForLevel.isEmpty()) {
            throw new RuntimeException("Sorry, no more questions left");
        }
        int index = getRandomIntegerWithMaxValue(questionsForLevel.size() - 1);
        return questionsForLevel.remove(index);
    }


    private int getRandomIntegerWithMaxValue(int maxValue) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(maxValue + 1);
    }

}
