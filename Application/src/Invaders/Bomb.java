package Invaders;

import Common.Sprite;
import Player.Defender;
import SpaceInvaders.GameConstants;

import java.util.Random;

public class Bomb extends Sprite
{
    private static int limit;
    private final static int HIT_VAL = 5;

    private Invader owner;

    public Bomb(int x, int y, Invader owner)
    {
        this.owner = owner;
        this.InitializeImage("src/images/bomb.png", null, null);
        this.x = x;
        this.y = y;
        this.Visible(false);
    }

    public void Fire(Defender defender)
    {
        //Create a random number in the range to limit (value indicating "rate of fire")
        Random random = new Random();
        int randNum = random.nextInt(limit);

        //Check to see if this bomb is dropped
        if(!this.Visible() && randNum == HIT_VAL)
        {
            //Set this bomb to visible
            this.Visible(true);
            this.x = this.owner.X();
            this.y = this.owner.Y();
        }

        //Check for collision with the defender
        if(defender.Visible() && this.Visible() && this.Collision(defender))
        {
            defender.Visible(false);
            this.Visible(false);
        }
        //Move the bomb down
        else if(this.Visible())
        {
            this.y++;

            //Check for collision with the floor
            if(this.y >= GameConstants.GROUND - this.imageHeight)
            {
                this.Visible(false);
            }
        }
    }

}