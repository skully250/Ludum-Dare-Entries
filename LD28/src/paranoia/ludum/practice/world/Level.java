package paranoia.ludum.practice.world.level;

import java.util.ArrayList;
import paranoia.ludum.one.lib.Sprite;
import paranoia.ludum.one.lib.SpriteLibrary;

/**
 *
 * @author Skully
 */
public abstract class Level {
     
    protected ArrayList<Sprite> sprites;
    protected ArrayList<Sprite> enemies;
    
    protected SpriteLibrary library;
    
    public Level(ArrayList<Sprite> sprites, SpriteLibrary library) {
        this.sprites = sprites;
        this.library = library;
        addSprites();
    }
    
    protected void addSprites() {
        for (int i = 0; i < sprites.size(); i++) {
            library.addSprite(sprites.get(i));
        }
    }
    
    protected void addEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            library.addSprite(sprites.get(i));
        }
    }
    
    protected void removeSprites() {
        for (int i = 0; i < sprites.size(); i++) {
            library.removeSprite(sprites.get(i).getName());
        }
    }
    
    public void unloadLevel() {
        removeSprites();
    }
    
    public abstract void createLevel(Sprite level);
}
