/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paranoia.ludum.practice.world.level;

import java.util.ArrayList;
import paranoia.ludum.one.lib.Sprite;
import paranoia.ludum.one.lib.SpriteLibrary;

/**
 *
 * @author Skully
 */
public class LevelOverworld extends Level {
    
    public LevelOverworld(ArrayList<Sprite> sprites, SpriteLibrary library) {
        super(sprites, library);
    }

    @Override
    public void createLevel(Sprite level) {
        this.library.addSprite(level);
        //TODO: Add level generation code ;)
    }
    
}
