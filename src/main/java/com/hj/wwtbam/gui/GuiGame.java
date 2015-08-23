package com.hj.wwtbam.gui;

import com.guigarage.flatterfx.FlatterFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by heiko on 22.08.15.
 */
public class GuiGame extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {



            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/gui/MainScreen.fxml"));

            BorderPane page = loader.load();

            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("WWTBAM");
            primaryStage.show();
            FlatterFX.style();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
