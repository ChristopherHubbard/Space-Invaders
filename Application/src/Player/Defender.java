package Player;

public class Defender
{
    private int numLives;
    private int location;
    private boolean firing = false;

    public Defender(int numLives, int location)
    {
        this.numLives = numLives;
        this.location = location;
    }

    public void Location(int location)
    {
        this.location = location;
    }

    public int Location()
    {
        return this.location;
    }

    public void Firing(boolean firing)
    {
        this.firing = firing;
    }

    public void Fire()
    {
        if(!this.firing)
        {
            //Fire here
            this.firing = true;
        }
    }

    public boolean Dead()
    {
        return this.numLives <= 0;
    }

    public void Hit()
    {
        this.numLives--;
    }
}
