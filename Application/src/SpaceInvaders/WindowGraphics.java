package SpaceInvaders;

import Barriers.Barriers;
import Common.GameConstants;
import Common.Sprite;
import Invaders.Invaders;
import Player.Defender;
import Invaders.Bomb;
import Player.Laser;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.*;
import javafx.util.Duration;

public class WindowGraphics extends JPanel implements Runnable
{
    private Dimension dimension;

    private Invaders invaders;
    private Defender defender;
    private Barriers barriers;

    private boolean playing = true;
    private String endMessage;

    private MediaPlayer mediaPlayer;

    public WindowGraphics(int numRowsInvaders, int numColsInvaders, int numLivesDefender, int bombLimit, int speed)
    {
        //Initialize the window specifications
        this.dimension = new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        //Initialize the game components and events
        this.invaders = new Invaders(numRowsInvaders, numColsInvaders, speed);
        this.defender = new Defender(numLivesDefender, this.invaders);
        this.barriers = new Barriers();

        //Initialize the static variables
        Laser.Barriers(this.barriers);
        Bomb.Limit(bombLimit);
        Bomb.Enemy(this.defender);
        Bomb.Barriers(this.barriers);

        this.addKeyListener(this.defender);

        //Make the game visible
        this.setVisible(true);
        this.PlayMusic();

        //Create a List of Threads
        List<Thread> threadList = new ArrayList<Thread>();

        //Start the invaders thread!
        threadList.add(new Thread(this));
        threadList.add(new Thread(this.invaders));
        threadList.add(new Thread(this.defender));

        //Start all the threads
        for(Thread thread : threadList)
        {
            thread.start();
        }

        //Wait for the threads to join?
    }

    public void PlayMusic()
    {
        //Need this JFXPanel to initialize the toolkit to prevent exception thrown!
        new JFXPanel();
        String soundPath = "Application/audio/spaceMusic.wav";
        //Initialize the media aspects for the sound effect
        Media media = new Media(new File(soundPath).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setVolume(1);
        //Loop the music
        this.mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        this.mediaPlayer.play();
    }

    public void DrawEndOfGame()
    {
        Graphics graphics = this.getGraphics();

        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        graphics.setColor(new Color(0, 32, 48));
        graphics.fillRect(50, GameConstants.SCREEN_WIDTH / 2 - 30, GameConstants.SCREEN_WIDTH - 100, 50);
        graphics.setColor(Color.white);
        graphics.drawRect(50, GameConstants.SCREEN_WIDTH / 2 - 30, GameConstants.SCREEN_WIDTH - 100, 50);

        Font font = new Font("Helvetica", Font.BOLD, 16);
        graphics.setFont(font);
        FontMetrics metrics = this.getFontMetrics(font);

        graphics.setColor(Color.WHITE);
        graphics.drawString(this.endMessage, (GameConstants.SCREEN_WIDTH - metrics.stringWidth(this.endMessage)) / 2, GameConstants.SCREEN_WIDTH / 2);
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        //Call the base paint component
        super.paintComponent(graphics);

        //Set color and draw the floor
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, this.dimension.width, this.dimension.height);
        graphics.setColor(Color.GREEN);

        //If in the game -- draw all the sprites
        if(this.playing)
        {
            graphics.drawLine(0, GameConstants.GROUND, GameConstants.SCREEN_WIDTH, GameConstants.GROUND);
            this.defender.DrawDefender(graphics, this);
            this.invaders.DrawInvaders(graphics, this);
            this.barriers.DrawBarriers(graphics, this);

            //Draw the score and number of lives
            graphics.drawString("Lives: " + this.defender.NumLives(), 5, 20);
            //graphics.drawString("Score: " + this.defender.Score().toString(), 430, 20);
        }
        graphics.drawString("Score: " + this.defender.Score().toString(), 430, 20);

        //Cleanup -- ensure that the buffer is up to date and then release resources
        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
    }

    @Override
    public void run()
    {
        while(this.playing)
        {
            this.repaint();

            if(this.invaders.Count() == 0)
            {
                Sprite.threadActive = false;
                this.playing = false;
                //Display the won message here
                this.endMessage = "You Won!!";
            }
            else if(this.defender.Lost())
            {
                Sprite.threadActive = false;
                this.playing = false;
                //Display the lost message here
                this.endMessage = "You Lose!!";
            }
            else if(Invaders.Invaded())
            {
                Sprite.threadActive = false;
                this.playing = false;
                //Display the end of game message
                this.endMessage = "You were invaded!!";
            }
        }

        //End all the other threads
        Sprite.threadActive = false;
        this.DrawEndOfGame();
    }
}
