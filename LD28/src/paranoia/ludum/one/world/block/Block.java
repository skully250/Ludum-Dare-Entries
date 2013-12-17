/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paranoia.ludum.two.world.block;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

/**
 *
 * @author Skully
 */
public abstract class Block {
    
    //Position and size of the image placed in the scene
    public int[] size;
    public Vector3f position;
    
    //Name and file location for the image aswell as an array for blocks
    public String name, fileLocation;
    public Picture[] blockList;
    
    //Other needed variables including bool for alpha support
    public boolean alpha;
    public Node guiNode;
    public AssetManager assetManager;
    
    public Block(int[] size, String name, String fileLocation, boolean alpha, SimpleApplication app, Vector3f position) {
        this.size = size;
        this.name = name;
        this.fileLocation = fileLocation;
        this.alpha = alpha;
        this.guiNode = app.getGuiNode();
        this.assetManager = app.getAssetManager();
        this.position = position;
    }
    
    public void render() {
        Picture block = new Picture(name);
        block.setImage(assetManager, fileLocation, alpha);
        block.setWidth(size[0]);
        block.setHeight(size[1]);
        guiNode.attachChild(block);
        block.setLocalTranslation(position);
    }
    
}
