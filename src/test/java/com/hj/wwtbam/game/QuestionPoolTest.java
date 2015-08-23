package com.hj.wwtbam.game;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by heiko on 19.08.15.
 */
public class QuestionPoolTest extends TestCase {


    @Test
    public void testPool() throws Exception {
        QuestionPool pool = new QuestionPoolBuilder().
                buildPoolFromFile (new File( this.getClass().getClassLoader().getResource("quiz.csv").toURI()));

        Question question = pool.getRandomQuestionForLevel(1);


        System.out.println(question.getQuestionText());
    }

}