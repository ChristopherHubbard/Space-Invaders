package Common;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.concurrent.TimeUnit;
import java.io.File;

public abstract class Sprite
{
    protected String imagePath;
    private boolean visible;
    protected Image image;
    protected int imageWidth;
    protected int imageHeight;

    protected String soundPath;
    protected MediaPlayer mediaPlayer;
    protected Media media;
    protected double mediaVolume;

    protected int x;
    protected int y;
    protected int speed;

    protected boolean damaged = false;

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

    public synchronized boolean Visible()
    {
        return visible;
    }

    public synchronized void Visible(boolean visible)
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

    public boolean Damaged()
    {
        return this.damaged;
    }

    public void Damaged(boolean damaged)
    {
        this.damaged = damaged;
    }

    protected void InitializeImage(String imagePath, ImageObserver heightObserver, ImageObserver widthObserver)
    {
        this.imagePath = imagePath;
        this.image = (new ImageIcon(this.imagePath)).getImage();
        this.imageWidth = this.image.getWidth(widthObserver);
        this.imageHeight = this.image.getHeight(heightObserver);
    }

    protected void InitializeSound(String soundPath, double volume)
    {
        //Need this JFXPanel to initialize the toolkit to prevent exception thrown!
        new JFXPanel();
        //Initialize the media aspects for the sound effect
        this.mediaVolume = volume;
        this.soundPath = soundPath;
        this.media = new Media(new File(this.soundPath).toURI().toString());
        this.mediaPlayer = new MediaPlayer(this.media);
        this.mediaPlayer.setVolume(this.mediaVolume);
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

    public void PlaySound()
    {
        try
        {
            this.InitializeSound(this.soundPath, this.mediaVolume);
            this.mediaPlayer.play();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void Delay()
    {
        try
        {
            Thread.sleep(GameConstants.DELAY);
        }
        catch(InterruptedException e)
        {
            System.out.println("Interrupted!!");
        }
    }

    public void Wait(int numMilli)
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(numMilli);
        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void Blink(int numBlinks)
    {
        for(int i = 0; i < numBlinks; i++)
        {
            this.Visible(false);
            this.Wait(500);
            this.Visible(true);
            this.Wait(100);

        }
        this.damaged = false;
    }

}
