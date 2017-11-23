package Player;

import Common.Sprite;
import Invaders.Invader;

import java.awt.event.KeyEvent;
import java.util.List;

public class Laser extends Sprite
{
    private final int HORIZONTAL_PADDING = 6;
    private final int VERTICAL_PADDING = 1;

    public Laser(int x, int y)
    {
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

    public void Fire(List<Invader> invaders)
    {
        //If the shot is being fired
        if(this.Visible())
        {
            //Check if any of the aliens have been hit
            for (Invader invader : invaders)
            {
                //Make sure that the invader is active and the shot is still visible -- check for collision
                if(invader.Visible() && this.Visible() && this.Collision(invader))
                {
                    //Remove the invader visibility and from the invaders container
                    invader.Visible(false);
                    invaders.remove(invader);
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
}
