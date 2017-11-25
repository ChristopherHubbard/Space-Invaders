package Invaders;

import Common.Sprite;

public class Invader extends Sprite
{
    private Bomb bomb;

    private boolean behindInvader = true;

    private int row;
    private int column;

    public Invader(int x, int y, int speed)
    {
        this.InitializeImage("Application/images/invader.jpg", null, null);
        this.x = x;
        this.y = y;
        this.bomb = new Bomb(x, y, this);
        this.speed = speed;
    }

    public void MoveHorizontal(int direction)
    {
        this.x += direction * this.speed;
    }

    public Bomb Bomb()
    {
        return this.bomb;
    }

    public boolean BehindInvader()
    {
        return this.behindInvader;
    }

    public void BehindInvader(boolean behindInvader)
    {
        this.behindInvader = behindInvader;
    }

    public void Row(int row)
    {
        this.row = row;
    }

    public void Column(int column)
    {
        this.column = column;
    }
}
