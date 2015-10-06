package com.hj.wwtbam.cli;

import com.hj.wwtbam.Game;
import com.hj.wwtbam.game.*;

import java.io.File;

/**
 * Created by heiko on 22.08.15.
 */
public class CliGame implements Game {



    private GameController gameController;

    private CommandLineController commandLineController;



    public void init() throws Exception {
        QuestionPool questionPool = QuestionPoolBuilder.create().
                buildPoolFromFile(new File(this.getClass().getClassLoader().getResource("quiz.csv").toURI()));

        gameController = new GameController(questionPool);
        commandLineController = new CommandLineController();
    }


    public void startGame() {
        commandLineController.printBanner();
        gameController.start();
        gameLoop();
    }

    private void gameLoop() {
        while(gameController.getStatus().equals(GameStatus.RUNNING)) {
            playNextRound();
        }
        gameOver();
    }

    private void gameOver() {
        if(gameController.getStatus().equals(GameStatus.LOST)) {
            commandLineController.printGameOver(gameController.getPrizeMoney());
        } else {
            commandLineController.printWonMessage();
        }

        if(commandLineController.askForNewGame()) {
            startGame();
        } else {
            System.exit(0);
        }

    }

    private void playNextRound() {
        commandLineController.printRound(gameController.getCurrentRound(),gameController.getPrizeMoney());
        Question question = gameController.nextQuestion();
        commandLineController.printQuestion(question);
        commandLineController.printAnswers(question);
        answerQuestion();
    }

    private void answerQuestion() {
        Answer answer = commandLineController.readAnwser();
        AnswerResult result = gameController.answerCurrentQuestion(answer);

        if(result.equals(AnswerResult.RIGHT)) {
            commandLineController.printCorrectAnswer();
        }
    }


}
