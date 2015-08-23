package com.hj.wwtbam.cli;

import com.hj.wwtbam.Game;
import com.hj.wwtbam.game.*;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by heiko on 22.08.15.
 */
public class CliGame implements Game {

    static final String ANSWER_PATTERN;

    static {
        String pattern = "^[";

        for (Answer answer : Answer.values()) {
            pattern+=answer.name().toLowerCase() + answer.name();
        }
        pattern+="]$";

        ANSWER_PATTERN = pattern;
    }


    private GameController gameController;

    public static void main(String [] args) throws Exception {
        CliGame game = new CliGame();
        game.init();
        game.startGame();
    }


    public void init() throws Exception {
        QuestionPool questionPool = new QuestionPoolBuilder().
                buildPoolFromFile(new File(this.getClass().getClassLoader().getResource("quiz.csv").toURI()));

        gameController = new GameController(questionPool);
    }


    public void startGame() {
        printBanner();
        gameController.start();
        playNextRound();
    }

    private void playNextRound() {
        printRound();
        Question question = gameController.nextQuestion();
        printQuestion(question);
        printAnswers(question);
        answerQuestion();
    }

    private void answerQuestion() {
        Answer answer = readAnwser();
        AnswerResult result = gameController.answerCurrentQuestion(answer);

        if(result.equals(AnswerResult.RIGHT)) {
            correctAnswer();
        } else {
            printGameOver();
        }
    }

    private void correctAnswer() {
        printCorrectAnswer();
        if(gameController.hasWon()) {
            printWonMessage();
        } else {
            playNextRound();
        }
    }

    private void printBanner() {
        System.out.println("Who wants to be a Millionaire?");
        System.out.println("==============================");
        System.out.println();
    }

    private void printRound() {
        System.out.println("Round: " + gameController.getCurrentRound());
        System.out.println("Money: " + gameController.getPrizeMoney());
    }


    private void printQuestion(Question question) {
        System.out.println("Question: " + question.getQuestionText());
    }

    private void printAnswers(Question question) {
        for (Answer answer : Answer.values()) {
            printAnswer(question,answer);
        }
    }

    private void printAnswer(Question question, Answer answer) {
        System.out.println(answer.name() + ": " + question.getAnswerText(answer));
    }


    private void printCorrectAnswer() {
        System.out.println("That was the correct answer!");
    }

    private void printGameOver() {
        System.out.println("Sorry, that answer was wrong :(");
        System.out.println("You've won " + gameController.getPrizeMoney() + "!");
    }

    private void printWonMessage() {
        System.out.println("Congratulation, you've answered all questions correctly!");
    }

    private Answer readAnwser() {
        String answerText = null;
        while(answerText==null) {
            Scanner scanner = new Scanner(System.in);
            promptForAnswers();
            try {
                answerText = scanner.next(ANSWER_PATTERN);
            } catch (InputMismatchException e) {

                answerText = null;
                System.out.println("Ooops, can't read that input!");
            }
        }

        return Answer.valueOf(answerText.toUpperCase());
    }


    private void promptForAnswers() {
        System.out.print("Please type an answer: ");
    }




}
