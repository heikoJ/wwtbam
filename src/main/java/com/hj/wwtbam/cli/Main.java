package com.hj.wwtbam.cli;

/**
 * Created by heiko on 05.10.15.
 */
public class Main {

    public static void main(String [] args) throws Exception {
        CliGame game = new CliGame();
        game.init();
        game.startGame();
    }

}
