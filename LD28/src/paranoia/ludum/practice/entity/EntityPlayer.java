package paranoia.ludum.practice.entity;

import paranoia.ludum.practice.world.level.Level;
import paranoia.ludum.one.lib.Sprite;

/**
 *
 * @author Skully
 */
public class EntityPlayer extends EntityMain {
    
    public int x, y;
    
    public EntityPlayer(Sprite sprite, Level level) {
        super(sprite, level);
        this.x = 0;
        this.y = 0;
    }
    
    public Sprite getSprite() {
        return this.entitySprite;
    }
    
    public EntityMain getEntity() {
        return this;
    }
    
}
