package Barriers;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Barriers
{
    private List<Barrier> barriers;

    public List<Barrier> Barriers()
    {
        return this.barriers;
    }

    public Barriers()
    {
        //Create all the barriers
        this.barriers = new ArrayList<Barrier>();
        for(int i = 0; i < 3; i++)
        {
            Barrier barrier = new Barrier(150 * i + 50, 250);
            this.barriers.add(barrier);
        }
    }

    public void DrawBarriers(Graphics graphics, ImageObserver observer)
    {
        for(Barrier barrier : this.barriers)
        {
            for(BarrierBlock block : barrier.BarrierBlocks())
            {
                if(!block.Damaged())
                {
                    block.DrawSprite(graphics, observer);
                }
            }
        }
    }


}
