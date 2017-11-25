package SpaceInvaders;

import Common.GameConstants;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame
{
    public static int numRowsInvaders = 5;
    public static int numColsInvaders = 5;
    public static int numLivesDefenders = 3;
    public static int bombLimit = 10;
    public static int speed = 2;

    public Game()
    {
        //Create the graphics
        this.setTitle("Space Invaders!");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
        this.setLocationRelativeTo(null);
        //Resizing is a pain :)
        this.setResizable(false);

        //Add the game!
        this.add(new WindowGraphics(Game.numRowsInvaders, Game.numColsInvaders, Game.numLivesDefenders, Game.bombLimit, Game.speed));
    }

    public static void main(String[] args)
    {
        //Set the game creation to process at the end of Swing's GUI thread
        EventQueue.invokeLater(() ->
        {
            Game game = new Game();
            game.setVisible(true);
        });
    }

}
