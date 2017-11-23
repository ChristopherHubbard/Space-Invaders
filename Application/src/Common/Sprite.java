package Common;

import javax.swing.*;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class Sprite
{
    protected String imagePath;
    private boolean visible;
    protected Image image;
    protected int imageWidth;
    protected int imageHeight;

    protected int x;
    protected int y;
    protected int speed;

    public Sprite()
    {
        this.visible = true;
    }

    public int X()
    {
        return this.x;
    }

    public int Y()
    {
        return this.y;
    }

    public boolean Visible()
    {
        return visible;
    }

    public void Visible(boolean visible)
    {
        this.visible = visible;
    }

    protected void InitializeImage(String imagePath, ImageObserver heightObserver, ImageObserver widthObserver)
    {
        this.imagePath = imagePath;
        this.image = (new ImageIcon(this.imagePath)).getImage();
        this.imageWidth = this.image.getWidth(widthObserver);
        this.imageHeight = this.image.getHeight(heightObserver);
    }

    public boolean Collision(Sprite sprite)
    {
        //Check if the two sprites have collided
        if(this.visible && sprite.visible && this.x >= sprite.x && this.x <= (sprite.x + sprite.imageWidth) && this.y >= sprite.y && this.y <= (sprite.y + sprite.imageHeight))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
