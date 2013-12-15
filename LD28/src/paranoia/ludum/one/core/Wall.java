/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paranoia.ludum.two.core;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

/**
 *
 * @author Skully
 */
public class Wall {
    
    public int x, y;
    public AssetManager assetManager;
    public Node guiNode;
    public Picture sprite;

    private String imageLocation;
    
    public Wall(int x, int y, String imageLocation, SimpleApplication app) {
        this.sprite = new Picture("Wall");
        this.x = x; this.y = y;
        this.imageLocation = imageLocation;
        this.assetManager = app.getAssetManager();
        this.guiNode = app.getGuiNode();
    }
    
    public void render() {
        sprite.setImage(assetManager, imageLocation, false);
        sprite.setPosition(x, y);
        sprite.setHeight(16);
        sprite.setWidth(16);
    }
}
