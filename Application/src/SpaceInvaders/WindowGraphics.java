package SpaceInvaders;

import Common.GameConstants;
import Common.Sprite;
import Invaders.Invaders;
import Invaders.Invader;
import Player.Defender;
import Invaders.Bomb;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WindowGraphics extends JPanel implements Runnable
{
    private Dimension dimension;

    private Invaders invaders;
    private Defender defender;

    private boolean playing = true;
    private String endMessage;

    public WindowGraphics(int numRowsInvaders, int numColsInvaders, int numLivesDefender, int bombLimit, int speed)
    {
        //Initialize the window specifications
        this.dimension = new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        //Initialize the game components and events
        this.invaders = new Invaders(numRowsInvaders, numColsInvaders, speed);
        this.defender = new Defender(numLivesDefender, this.invaders.InvaderList());
        Bomb.Limit(bombLimit);
        Bomb.Enemy(this.defender);
        this.addKeyListener(this.defender);

        //Make the game visible
        this.setVisible(true);

        //Create a List of Threads
        List<Thread> threadList = new ArrayList<Thread>();

        //Start the invaders thread!
        threadList.add(new Thread(this));
        threadList.add(new Thread(this.invaders));
        threadList.add(new Thread(this.defender));
        threadList.add(new Thread(this.defender.Laser()));

        //Start all the threads
        for(Thread thread : threadList)
        {
            thread.start();
        }

        //Wait for the threads to join?
        for(Thread thread : threadList)
        {
            try
            {
               thread.join();
            }
            catch(InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
        }

        //All the threads are joined here -- end the game
        DrawEndOfGame();
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

        graphics.setFont(new Font("Helvetica", Font.BOLD, 16));
        FontMetrics metrics = this.getFontMetrics(Font.getFont("Helvetica"));

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
        }

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

            if(this.invaders.InvaderList().size() == 0)
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
    }
}
