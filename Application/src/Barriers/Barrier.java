package Barriers;

import java.util.List;
import java.util.ArrayList;

public class Barrier
{
    private int startX;
    private int startY;

    private List<BarrierBlock> barrierBlocks;

    private int numBlocks = 16;

    public List<BarrierBlock> BarrierBlocks()
    {
        return this.barrierBlocks;
    }

    public int NumBlocks()
    {
        return this.numBlocks;
    }

    public void NumBlocks(int numBlocks)
    {
        this.numBlocks = numBlocks;
    }

    public Barrier(int startX, int startY)
    {
        //Set the starting x and y position to keep track of the different barriers
        this.startX = startX;
        this.startY = startY;

        //Create the list of the four by four barrier blocks
        this.barrierBlocks = new ArrayList<BarrierBlock>();
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                BarrierBlock block = new BarrierBlock(startX + 12 * i, startY + 15 * j);
                this.barrierBlocks.add(block);
            }
        }
    }
}
