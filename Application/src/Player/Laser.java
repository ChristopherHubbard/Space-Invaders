package Player;

import Common.Sprite;
import Invaders.Invader;
import Invaders.Invaders;
import Barriers.*;

import java.awt.event.KeyEvent;

public class Laser extends Sprite implements Runnable
{
    private final int HORIZONTAL_PADDING = 6;
    private final int VERTICAL_PADDING = 1;

    private Invaders invaders;

    private static Barriers barriers;

    public static void Barriers(Barriers barriers)
    {
        Laser.barriers = barriers;
    }

    public Laser(int x, int y, Invaders invaders)
    {
        this.invaders = invaders;
        this.InitializeImage("Application/images/laser.png", null, null);
        this.InitializeSound("Application/audio/laserSound.wav", 0.5);

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
            this.PlaySound();
        }
    }

    public void Fire()
    {
        //If the shot is being fired
        if(this.Visible())
        {
            //Check if any of the aliens have been hit
            for (Invader invader : this.invaders.InvaderList())
            {
                //Make sure that the invader is active and the shot is still visible -- check for collision
                if(invader.Visible() && this.Visible() && this.Collision(invader))
                {
                    //Remove the invader visibility and from the invaders container
                    invader.Visible(false);
                    this.invaders.UpdateBehindInvader();
                    this.invaders.Count(this.invaders.Count() - 1);
                    this.Visible(false);

                    //Increase the total score by 1
                    Defender.Score(Defender.Score() + 1);
                }

            }

            //Check if any of the barriers have been hit
            for(Barrier barrier : this.barriers.Barriers())
            {
                for(BarrierBlock block : barrier.BarrierBlocks())
                {
                    if(!block.Damaged() && this.Visible() && this.Collision(block))
                    {
                        //Make the laser "die" on contact with a block!
                        this.Visible(false);
                    }
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
            Sprite.Delay();
            this.Fire();
        }
    }
}
