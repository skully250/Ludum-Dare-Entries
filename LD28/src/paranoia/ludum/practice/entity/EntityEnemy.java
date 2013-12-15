package paranoia.ludum.practice.entity;

import paranoia.ludum.practice.world.level.Level;
import paranoia.ludum.one.lib.Sprite;

/**
 *
 * @author Skully
 */
public class EntityEnemy extends EntityMain {
    
    public EntityEnemy(Sprite sprite, Level level) {
        super(sprite, level);
    }
    
    public Sprite getSprite() {
        return this.entitySprite;
    }
    
    @Override
    public EntityMain getEntity() {
        return this;
    }
    
}
