package Player;

import Common.Sprite;
import Common.GameConstants;
import Invaders.Invader;
import java.util.List;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Defender extends Sprite implements KeyListener, Runnable
{
    //Starting X and Y positions for the defender
    private final int START_X = 270;
    private final int START_Y = 280;

    //Number of lives for the defender (selected in menu)
    private int numLives;
    //Laser for the Defender to fire
    private Laser laser;

    public Laser Laser()
    {
        return this.laser;
    }

    public Defender(int numLives, List<Invader> invaders)
    {
        this.InitializeImage("src/images/defender.png", null, null);
        this.numLives = numLives;
        this.x = START_X;
        this.y = START_Y;
        this.laser = new Laser(this.x, this.y, invaders);
    }

    public boolean Lost()
    {
        //Game lost if the defender's lives is less than 0
        return this.numLives <= 0;
    }

    public void Hit()
    {
        //Decrease the number of lives if hit
        this.numLives--;
    }

    public void Move()
    {
        //Increase horizontal position by speed
        this.x += this.speed;

        //Cutoff the position
        if (this.x <= 2)
        {
            this.x = 2;
        }
        else if (this.x >= GameConstants.SCREEN_WIDTH - 2 * this.imageWidth)
        {
            this.x = GameConstants.SCREEN_WIDTH - 2 * this.imageWidth;
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        //Nothing to do here!
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        //Move in the appropriate direction
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            this.speed = -2;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            this.speed = 2;
        }
        //Check to see if the laser is firing
        else if(!this.laser.Visible())
        {
            this.laser.KeyPressed(e, this.x, this.y);
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        //If the left or right button was pressed -- set speed to zero
        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            this.speed = 0;
        }
    }

    //Run method to check to movement of the defender
    @Override
    public void run()
    {
        while(Sprite.threadActive)
        {
            this.Move();
        }
    }
}
