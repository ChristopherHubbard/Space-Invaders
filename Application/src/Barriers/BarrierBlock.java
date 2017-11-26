package Barriers;

import Common.Sprite;

public class BarrierBlock extends Sprite
{
    public BarrierBlock(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.InitializeImage("Application/images/barrierBlock.png", null, null);
    }
}
