package com.hj.wwtbam.game;

import java.util.Optional;

/**
 * Created by heiko on 19.08.15.
 */
public class GameController {

    private int money ;
    private int moneyIncrease = 50;

    private int round;
    private int maxRound = 5;

    private GameStatus status = GameStatus.NOT_STARTED;

    private QuestionPool questionPool;

    private Optional<Question> currentQuestion;


    public GameController(QuestionPool questionPool) {
        this.questionPool = questionPool;
    }

    public void start() {
        money = 0;
        round = 0;
        status = GameStatus.RUNNING;
        currentQuestion = Optional.empty();
    }

    public Question nextQuestion() {
        checkGameStatus();
        if(!currentQuestion.isPresent()) {
            round++;
            currentQuestion = Optional.of(questionPool.getRandomQuestionForLevel(round));
        }
        return currentQuestion.get();
    }

    public AnswerResult answerCurrentQuestion(Answer answer) {
        checkGameStatus();

        return answerQuestion(currentQuestion.get(), answer);
    }


    private AnswerResult answerQuestion(Question question, Answer answer) {
        if(question.isCorrectAnswer(answer)) {
            increasePrizeMoney();
            if(isLastRound()) {
                status = GameStatus.WON;
            }
            currentQuestion = Optional.empty();
            return AnswerResult.RIGHT;
        } else {
            status = GameStatus.LOST;
            return AnswerResult.WRONG;
        }
    }


    private boolean isLastRound() {
        return (round == maxRound);
    }

    private void increasePrizeMoney() {
        money += moneyIncrease;
    }

    public int getPrizeMoney() {
        return money;
    }


    public GameStatus getStatus() {
        return status;
    }


    private void checkGameStatus() {
        if(status.equals(GameStatus.LOST) || status.equals(GameStatus.WON)) {
            throw new GameOverException();
        }
    }

    public int getCurrentRound() {
        return round;
    }

      public boolean hasWon() {
          return GameStatus.WON.equals(status);
      }


}
