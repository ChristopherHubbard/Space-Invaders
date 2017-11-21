package Invaders;

import java.util.Random;

public class Invader
{
    private final static int BOUND = 50;

    private int x;
    private int y;

    private int speed;
    private boolean canFire = false;
    private boolean firedThisRound = false;
    private boolean dead = false;

    public Invader(int x, int y, int speed)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void Fire(int randNum)
    {
        if(this.canFire && !this.firedThisRound && randNum > BOUND)
        {
            //Fire
        }
    }

    public void MoveHorizontal(int direction)
    {
        this.x += direction * this.speed;
    }

}
