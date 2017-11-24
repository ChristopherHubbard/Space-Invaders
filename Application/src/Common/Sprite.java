package Common;

import javax.swing.*;
import java.awt.*;
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

    public static boolean threadActive = true;

    public Sprite()
    {
        this.visible = true;
    }

    public Image Image()
    {
        return this.image;
    }

    public int X()
    {
        return this.x;
    }

    public int Y()
    {
        return this.y;
    }

    public void X(int x)
    {
        this.x = x;
    }

    public void Y(int y)
    {
        this.y = y;
    }

    public boolean Visible()
    {
        return visible;
    }

    public void Visible(boolean visible)
    {
        this.visible = visible;
    }

    public int ImageWidth()
    {
        return this.imageWidth;
    }

    public int ImageHeight()
    {
        return this.imageHeight;
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
        return this.visible && sprite.visible && this.x >= sprite.x && this.x <= (sprite.x + sprite.imageWidth) && this.y >= sprite.y && this.y <= (sprite.y + sprite.imageHeight);
    }

    public void DrawSprite(Graphics graphics, ImageObserver observer)
    {
        if(this.visible)
        {
            graphics.drawImage(this.image, this.x, this.y, observer);
        }
    }

}
