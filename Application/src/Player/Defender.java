package Player;

import Common.Sprite;
import Common.GameConstants;
import Invaders.Invaders;

import java.awt.image.ImageObserver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class Defender extends Sprite implements KeyListener, Runnable
{
    //Starting X and Y positions for the defender
    private final int START_X = 380;
    private final int START_Y = 380;

    //Number of lives for the defender (selected in menu)
    private int numLives;
    //Laser for the Defender to fire
    private Laser laser;

    public Laser Laser()
    {
        return this.laser;
    }

    public Defender(int numLives, Invaders invaders)
    {
        this.InitializeImage("Application/images/defender.png", null, null);
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
        //Oscillate visible and not if the number of lives is greater than zero
        if(this.numLives > 0)
        {
            this.damaged = true;
        }
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

    public void DrawDefender(Graphics graphics, ImageObserver observer)
    {
        if(this.Visible())
        {
            this.DrawSprite(graphics, observer);
            this.laser.DrawSprite(graphics, observer);
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
        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
        {
            this.speed = -2;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
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
        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
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
            Sprite.Delay();
            this.Move();
            this.laser.Fire();

            //Blink character if hit
            if(this.damaged)
            {
                this.Blink(2);
            }
        }
    }
}
