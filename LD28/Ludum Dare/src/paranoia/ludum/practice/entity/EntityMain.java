package paranoia.ludum.practice.entity;

import paranoia.ludum.practice.core.Main;
import paranoia.ludum.practice.world.level.Level;
import paranoia.ludum.one.lib.Sprite;

/**
 *
 * @author Skully
 */
public abstract class EntityMain implements Entity {
    
    protected Level level;
    protected EntityMain entity;
    protected Sprite entitySprite;
    
    public EntityMain(Sprite sprite, Level level) {
       this.entitySprite = sprite;
       this.level = level;
       render();
    }

    public void render() { Main.sprites.add(this.entitySprite); }
    
    public abstract Sprite getSprite();

    public abstract EntityMain getEntity();

    public Level getEntityLevel() { return this.level; }
    
}
