package Player;

import Common.Sprite;
import SpaceInvaders.GameConstants;

import java.awt.event.KeyEvent;

public class Defender extends Sprite
{
    //Starting X and Y positions for the defender
    private final int START_X = 270;
    private final int START_Y = 280;

    //Number of lives for the defender (selected in menu)
    private int numLives;
    //Laser for the Defender to fire
    private Laser laser;

    public Defender(int numLives)
    {
        this.InitializeImage("src/images/defender.png", null, null);
        this.numLives = numLives;
        this.x = START_X;
        this.y = START_Y;
        this.laser = new Laser(this.x, this.y);
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

    public void KeyPressed(KeyEvent e)
    {
        //Move in the appropriate direction
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            this.speed = -2;
            return;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            this.speed = 2;
            return;
        }
        //Check to see if the laser is firing
        else if(!this.laser.Visible())
        {
            this.laser.KeyPressed(e, this.x, this.y);
        }
    }

    public void KeyReleased(KeyEvent e)
    {
        //If the left or right button was pressed -- set speed to zero
        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            this.speed = 0;
        }
    }
}
