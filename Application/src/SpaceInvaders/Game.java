package SpaceInvaders;

import Common.GameConstants;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class Game extends JFrame
{
    public static int numRowsInvaders;
    public static int numColsInvaders;
    public static int numLivesDefenders;
    public static int bombLimit;
    public static int speed;

    public Game()
    {
        //Get the static fields as input
        Game.numRowsInvaders = Game.Read("number of rows of invaders: ");
        Game.numColsInvaders = Game.Read("number of columns of invaders: ");
        Game.numLivesDefenders = Game.Read("number of lives for the player: ");

        //Make sure Game.bombLimit won't cause an exception to be thrown in Random()
        do
        {
            Game.bombLimit = Game.Read("rate the bombs fire (value 1 - MAX_INT where lower numbers increase speed): ");
        } while(Game.bombLimit < 1);

        Game.speed = Game.Read("speed the invaders move (higher numbers are faster - gets fast quick): ");

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

    private static int Read(String string)
    {
        System.out.println("Enter the " + string);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
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
