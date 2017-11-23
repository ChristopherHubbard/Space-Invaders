package Player;

import Common.Sprite;
import Invaders.Invader;

import java.awt.event.KeyEvent;
import java.util.List;

public class Laser extends Sprite implements Runnable
{
    private final int HORIZONTAL_PADDING = 6;
    private final int VERTICAL_PADDING = 1;

    private List<Invader> invaders;

    public Laser(int x, int y, List<Invader> invaders)
    {
        this.invaders = invaders;
        this.InitializeImage("src/images/laser.png", null, null);

        this.x = x + HORIZONTAL_PADDING;
        this.y = y - VERTICAL_PADDING;
        //Laser is initially invisible
        this.Visible(false);
    }

    public void KeyPressed(KeyEvent e, int x, int y)
    {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            this.x = x;
            this.y = y;
            this.Visible(true);
        }
    }

    public void Fire()
    {
        //If the shot is being fired
        if(this.Visible())
        {
            //Check if any of the aliens have been hit
            for (Invader invader : this.invaders)
            {
                //Make sure that the invader is active and the shot is still visible -- check for collision
                if(invader.Visible() && this.Visible() && this.Collision(invader))
                {
                    //Remove the invader visibility and from the invaders container
                    invader.Visible(false);
                    this.invaders.remove(invader);
                    this.Visible(false);
                }

            }

            //Move the laser beam
            this.y -= 4;
            //Check to see if flew off the screen
            if(this. y < 0)
            {
                this.Visible(false);
            }
        }
    }

    //Run method to call Fire
    @Override
    public void run()
    {
        while(Sprite.threadActive)
        {
            this.Fire();
        }
    }
}
