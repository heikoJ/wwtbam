package com.hj.wwtbam.gui;

import com.hj.wwtbam.game.*;
import com.hj.wwtbam.util.ExceptionalProducer;
import com.hj.wwtbam.util.ExceptionalRunnable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;



/**
 * Created by heiko on 22.08.15.
 */
public class MainScreenController implements Initializable {

    @FXML
    Button buttonA;

    @FXML
    Button buttonB;

    @FXML
    Button buttonC;

    @FXML
    Button buttonD;

    @FXML
    Label questionLabel;

    @FXML
    Button buttonNewGame;


    @FXML
    Label labelRound;

    @FXML
    Label labelMoney;

    Button[] answerButtons;


    private GameController gameController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        answerButtons = new Button[] {buttonA,buttonB,buttonC,buttonD};

        int i=0;
        for (Button answerButton : answerButtons) {
            answerButton.setUserData(Answer.values()[i++]);
            answerButton.setAlignment(Pos.BASELINE_LEFT);
            answerButton.setOnAction(event -> {
                executeAndCatch( ()-> {
                    Answer answer = (Answer) answerButton.getUserData();
                    answerQuestion(answer);
                });
            });
        }

        disableAnswerButtons();

        QuestionPool questionPool = QuestionPoolBuilder.create().
                buildPoolFromFile(getQuestonFile());
        gameController = new GameController(questionPool);



    }

    private File getQuestonFile()   {
        return
            new File(produceAndCatch(() -> this.getClass().getClassLoader().getResource("quisz.csv").toURI()));

    }


    public void newGame() {

        executeAndCatch(() -> {
            gameController.start();
            buttonNewGame.setDisable(true);
            nextRound();
        });
    }

    private void nextRound() {
        updateGameInfo();
        Question question = gameController.nextQuestion();
        questionLabel.setText(question.getQuestionText());
        setAnswers(question);
        enableAnswerButtons();
    }

    private void setAnswers(Question question) {
        int i=0;
        for(Answer answer : Answer.values()) {
            answerButtons[i++].setText(answer.name() + " " + question.getAnswerText(answer));
        }
    }


    private void answerQuestion(Answer answer) {
        AnswerResult result = gameController.answerCurrentQuestion(answer);

        if(result.equals(AnswerResult.RIGHT)) {
            showNotification("Correct Answer","That answer was correct!");
            checkGameWon();
        } else {
            disableAnswerButtons();
            showNotification("Bad Luck!","That answer was wrong!");
            disableAnswerButtons();
            buttonNewGame.setDisable(false);
        }
    }

    private void checkGameWon() {
        if(gameController.hasWon()) {
            updateGameInfo();
            showNotification("You won!","You made it! You've won " + gameController.getPrizeMoney() + "€");
            buttonNewGame.setDisable(false);
            disableAnswerButtons();
        }  else {
            nextRound();
        }
    }


    private void disableAnswerButtons() {
        setButtonStatus(true);
    }

    private void enableAnswerButtons(){
        setButtonStatus(false);
    }

    private void updateGameInfo() {
        labelRound.setText("Round: " + (gameController.getCurrentRound() ));
        labelMoney.setText("Money: " + gameController.getPrizeMoney() + "€");
    }



    private void setButtonStatus(boolean status) {
        for (Button answerButton : answerButtons) {
            answerButton.setDisable(status);
        }
    }


    public static void showNotification(String title, String text) {
        Notifications.create().
                title(title).
                text(text).
                position(Pos.CENTER).
                showInformation();
    }

    public static void showError(String text) {
        Notifications.create().
                title("Error").
                text(text).
                position(Pos.CENTER).
                showError();
    }


    private static void executeAndCatch(ExceptionalRunnable runnable) {
        try {
            runnable.run();
        } catch(Exception e) {
            showError(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private  static <T> T  produceAndCatch(ExceptionalProducer<T,Exception> producer) {
        try {
            return producer.produce();
        } catch(Exception e) {
            e.printStackTrace();
            showError(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
