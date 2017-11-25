package Invaders;

import Common.GameConstants;
import Common.Sprite;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Invaders implements Runnable
{
    private static final int INITIAL_X = 150;
    private static final int INITIAL_Y = 5;

    private int direction = 1;

    private int numRows;
    private int numCols;

    private List<Invader> invaders;
    private static boolean invaded = false;
    private int count;

    public List<Invader> InvaderList()
    {
        return this.invaders;
    }

    public static boolean Invaded()
    {
        return Invaders.invaded;
    }

    public int Count()
    {
        return this.count;
    }

    public void Count(int count)
    {
        this.count = count;
    }

    public Invaders(int numRows, int numCols, int speed)
    {
        //Initialize the rows and columns of the invaders
        this.numRows = numRows;
        this.numCols = numCols;

        //Create the list of invaders
        this.invaders = new ArrayList<Invader>();
        for(int i = 0; i < numRows; i++)
        {
            for(int j = 0; j < numCols; j++)
            {
                //Create an invader in the appropriate position
                Invader invader = new Invader(Invaders.INITIAL_X + 25 * j, Invaders.INITIAL_Y + 25 * i, speed);
                invader.Row(i);
                invader.Column(j);
                //Add the invader to the invader list
                this.invaders.add(invader);
            }
        }
        //Set the number of invaders
        this.count = this.invaders.size();
    }

    public void Move()
    {
        for (Invader invader : this.invaders)
        {
            if((invader.X() >= GameConstants.SCREEN_WIDTH - GameConstants.RIGHT_BORDER && this.direction != -1) || (invader.X() <= GameConstants.LEFT_BORDER && this.direction != 1))
            {
                //Change the direction of the invaders
                this.direction = -this.direction;

                //Move all the invaders down
                for(Invader nextInvader : this.invaders)
                {
                    nextInvader.Y(nextInvader.Y() + GameConstants.DOWN);
                }
            }
        }
    }

    public void InvasionStatus()
    {
        //Check all the invaders
        for(Invader invader : this.invaders)
        {
            //If the invader is still present
            if(invader.Visible())
            {
                //Check the invader position and move the invader if appropriate
                if(invader.Y() > GameConstants.GROUND - invader.ImageHeight())
                {
                    Invaders.invaded = true;
                }
                else
                {
                    invader.MoveHorizontal(this.direction);
                }
            }
        }
    }

    public void FireBombs()
    {
        //Try to fire bombs for all invaders -- may need to update so only fire when no one below the invader
        for(Invader invader : this.invaders)
        {
            if(invader.Visible() || invader.Bomb().Visible())
            {
                invader.Bomb().Fire();
            }
        }
    }

    public void UpdateBehindInvader()
    {
        //Update for all the invaders
        for(Invader invader : this.invaders)
        {
            if(invader.Visible())
            {
                boolean behind = false;
                for(Invader ahead : this.invaders)
                {
                    if(ahead.Visible() && invader.X() == ahead.X() && invader.Y() < ahead.Y())
                    {
                        behind = true;
                    }
                }

                //Set the behind variable
                invader.BehindInvader(behind);
            }
        }
    }

    public void DrawInvaders(Graphics graphics, ImageObserver observer)
    {
        //For each of the invaders
        for(Invader invader : this.invaders)
        {
            //Draw the invader and draw the bomb
            invader.DrawSprite(graphics, observer);
            invader.Bomb().DrawSprite(graphics, observer);
        }
    }

    @Override
    public void run()
    {
        //Run while the game is active
        while(Sprite.threadActive)
        {
            //Add a delay
            Sprite.Delay();
            this.Move();
            this.InvasionStatus();
            this.UpdateBehindInvader();
            this.FireBombs();
        }
    }

}
