package com.hj.wwtbam.game;

import java.util.Map;

/**
 * Created by heiko on 19.08.15.
 */
public class Question {

    private String text;

    private Map<Answer,String> answers;

    private Answer correctAnswer;

    Question(String text,Map<Answer,String> answers,Answer correctAnswer) {
        this.text = text;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return text;
    }

    public String getAnswerText(Answer answer) {
        return answers.get(answer);
    }

    boolean isCorrectAnswer(Answer answer) {
        return correctAnswer.equals(answer);
    }


}
