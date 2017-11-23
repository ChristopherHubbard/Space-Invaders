package Invaders;

import Common.Sprite;
import Player.Defender;

public class Invader extends Sprite
{
    private final static int HIT_VAL = 5;

    private Bomb bomb;

    public Invader(int x, int y, int speed)
    {
        this.InitializeImage("src/images/invader.png", null, null);
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
}
