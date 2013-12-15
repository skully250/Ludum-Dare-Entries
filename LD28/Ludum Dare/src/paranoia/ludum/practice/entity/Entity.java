package paranoia.ludum.practice.entity;

import paranoia.ludum.practice.world.level.Level;

/**
 *
 * @author Skully
 */
public interface Entity {
    
    public void render();
    
    public Entity getEntity();
    
    public Level getEntityLevel();
    
}
