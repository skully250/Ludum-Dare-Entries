package paranoia.ludum.two.core;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

/**
 *
 * @author Skully
 */
public class World {
    
    SimpleApplication app;
    AppSettings settings;
    AssetManager assetManager;
    Node guiNode;
    
    Wall wall;
    public Picture[][] lSprites;
    
    public World(SimpleApplication app, AppSettings settings) {
        this.app = app;
        this.settings = settings;
        this.assetManager = app.getAssetManager();
        this.guiNode = app.getGuiNode();
        lSprites = new Picture[1024][768];
    }
    
    public void renderEntities() {
        
    }
    
    public void renderItems() {
        
    }
    
    public boolean canMove(int newX, int newY) {
        if (lSprites[newY][newX] == wall.sprite) return false;
        else return true;
    }
    
    public void generateLevel() {
        for (int y = 0; y < 1024; y += 16) {
            for (int x = 0; x < 768; x += 16) {
                if (y <= 48 && x < 128) {
                    wall = new Wall(x, y, "Textures/Terrain/dirt.jpg", app);
                } else {
                    wall = new Wall(x, y, "Textures/Terrain/grass.png", app);
                }
                lSprites[y][x] = wall.sprite;
                wall.render();
                guiNode.attachChild(lSprites[y][x]);
            }
        }
    }
}