package com.hj.wwtbam.cli;

import com.hj.wwtbam.game.Answer;
import com.hj.wwtbam.game.Question;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by heiko on 05.10.15.
 */
public class CommandLineController {

    static final String ANSWER_PATTERN;

    static {
        String pattern = "^[";

        for (Answer answer : Answer.values()) {
            pattern+=answer.name().toLowerCase() + answer.name();
        }
        pattern+="]$";

        ANSWER_PATTERN = pattern;
    }

    boolean askForNewGame() {
        System.out.println("Start new game ?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if("Y".equalsIgnoreCase(input)) {
            return true;
        } else {
            System.out.println("Goodbye");
            return false;
        }
    }

    void printBanner() {
        System.out.println("Who wants to be a Millionaire?");
        System.out.println("==============================");
        System.out.println();
    }

    void printRound(int round, float money) {
        System.out.println("Round: " + round);
        System.out.println("Money: " + money);
    }

    void printQuestion(Question question) {
        System.out.println("Question: " + question.getQuestionText());
    }

    void printAnswers(Question question) {
        for (Answer answer : Answer.values()) {
            printAnswer(question, answer);
        }
    }

    private void printAnswer(Question question, Answer answer) {
        System.out.println(answer.name() + ": " + question.getAnswerText(answer));
    }

    void printCorrectAnswer() {
        System.out.println("That was the correct answer!");
    }

    void printGameOver(int money) {
        System.out.println("Sorry, that answer was wrong :(");
        System.out.println("You've won " + money + "!");
    }

    void printWonMessage() {
        System.out.println("Congratulation, you've answered all questions correctly!");
    }

    Answer readAnwser() {
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
