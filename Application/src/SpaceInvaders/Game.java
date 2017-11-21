package SpaceInvaders;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import SpaceInvaders.WindowGraphics;
import Player.Defender;

public class Game extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        //Initialize the windows and the initial data using FXML page?
        int numLives = 3;
        int location = 0;

        Defender player = new Defender(numLives, location);

        Group root = new Group();
        WindowGraphics.player = player;
        Scene scene = WindowGraphics.InitializeGraphics(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Invaders");
        primaryStage.show();
    }
}
