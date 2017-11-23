package SpaceInvaders;

import Common.GameConstants;
import Common.Sprite;
import Invaders.Invaders;
import Player.Defender;
import Invaders.Bomb;

import javax.swing.*;
import java.awt.*;

public class WindowGraphics extends JPanel implements Runnable
{
    private Dimension dimension;
    private Invaders invaders;
    private Defender defender;
    private Thread gameThread;
    private Thread invadersThread;
    private Thread defenderThread;
    private Thread laserThread;
    private boolean playing = true;
    private String gameOver = "Game Over!";
    private String won = "You Win!!";
    private String invaded = "Invasion! You Lose!";

    public WindowGraphics(int numRowsInvaders, int numColsInvaders, int numLivesDefender, int bombLimit, int speed)
    {
        //Initialize the window specifications
        this.dimension = new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        //Initialize the game components and events
        this.invaders = new Invaders(numRowsInvaders, numColsInvaders, speed);
        this.defender = new Defender(numLivesDefender, this.invaders.InvaderList());
        Bomb.Limit(bombLimit);
        Bomb.Enemy(this.defender);
        this.addKeyListener(this.defender);

        //Start the invaders thread!
        this.gameThread = new Thread(this);
        this.invadersThread = new Thread(this.invaders);
        this.defenderThread = new Thread(this.defender);
        this.laserThread = new Thread(this.defender.Laser());

        //Start all the threads
        this.gameThread.start();
        this.invadersThread.start();
        this.defenderThread.start();
        this.laserThread.start();

        //Wait for the threads to join?
    }

    public void DrawGame()
    {
        //Draw all the graphics for the game here
    }

    @Override
    public void run()
    {
        while(this.playing)
        {
            this.DrawGame();

            if(this.invaders.InvaderList().size() == 0)
            {
                Sprite.threadActive = false;
                this.playing = false;
                //Display the won message here
            }
            else if(this.defender.Lost())
            {
                Sprite.threadActive = false;
                this.playing = false;
                //Display the lost message here
            }
            else if(Invaders.Invaded())
            {
                Sprite.threadActive = false;
                this.playing = false;
                //Display the end of game message
            }

        }

    }
}
