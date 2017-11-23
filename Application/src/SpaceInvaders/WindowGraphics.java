package SpaceInvaders;

import Player.Defender;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

class WindowGraphics
{
    static Defender player;

    static Scene InitializeGraphics(Group root)
    {
        Scene scene = new Scene(root, 600, 400);
        //Events that occur when button is pressed
        scene.setOnKeyPressed(event ->
        {
            //Fire bullet
            if (event.getCode() == KeyCode.SPACE)
            {
                //Call the defender's fire method
                player.Fire();
            }
        });

        //Reset firing when space bar released
        scene.setOnKeyReleased(event ->
        {
            if (event.getCode() == KeyCode.SPACE)
            {
                player.Firing(false);
            }
        });
        return scene;
    }

}
