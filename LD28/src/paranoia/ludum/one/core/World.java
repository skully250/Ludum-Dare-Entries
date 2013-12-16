package paranoia.ludum.two.core;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import java.util.Random;

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
    Wall block;
    
    public Picture[][] lSprites;
    public Picture[][] fSprites;
    public Picture[] itemList;
    
    public World(SimpleApplication app, AppSettings settings) {
        this.app = app;
        this.settings = settings;
        this.assetManager = app.getAssetManager();
        this.guiNode = app.getGuiNode();
        //These are really unnecessary but ill simplify later
        int gridY = settings.getHeight() / ApplicationMain.GRID_SIZE;
        int gridX = settings.getWidth() / ApplicationMain.GRID_SIZE;
        itemList = new Picture[10];
        lSprites = new Picture[gridY][gridX];
        fSprites = new Picture[gridY][gridX];
    }
    
    public void addItems() {
        Picture burger = new Picture("Burger");
        Picture tuna = new Picture("Tuna");
        Picture donut = new Picture("Donut");
        Picture fries = new Picture("Fries");
        burger.setImage(assetManager, "Textures/Extras/Food/Burger.png", true);
        tuna.setImage(assetManager, "Textures/Extras/Food/canoftuna.png", true);
        donut.setImage(assetManager, "Textures/Extras/Food/donut.png", true);
        fries.setImage(assetManager, "Textures/Extras/Food/fries.png", true);
        itemList[0] = burger;
        itemList[1] = tuna;
        itemList[2] = donut;
        itemList[3] = fries;
    }
    
    public void renderEntities() {
        
    }
    
    public void renderItems() {
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            int rando = rand.nextInt(3);
            System.out.println(rando + " is the random number");
            if (rando == 3 || rando == 2) {
                int ix = rand.nextInt(46);
                int iy = rand.nextInt(46);
                System.out.println(ix + " " + iy);
                int index = rand.nextInt(4);
                itemList[index].setLocalTranslation(ix * 16, iy * 16, 3);
                itemList[index].setWidth(16); itemList[index].setHeight(16);
                fSprites[iy][ix] = itemList[index];
                guiNode.attachChild(itemList[index]);
                System.out.println(itemList[index] + " added");
            }
        }
    }
    
    public boolean canMove(int newX, int newY) {
        return true;
    }
    
    public void generateLevel() {
        for (int y = 0; y < settings.getHeight(); y += ApplicationMain.GRID_SIZE) {
            for (int x = 0; x < settings.getWidth(); x += ApplicationMain.GRID_SIZE) {
                int gridY = y / 16;
                int gridX = y / 16;
                System.out.println(gridY + " " + gridX);
                if (y <= 48 && x < 128) {
                    wall = new Wall(x, y, "Textures/Terrain/planks.png", app);
                } else if (y <= 64 && x < 144) {
                    wall = new Wall(x, y, "Textures/Terrain/wall.png", app);
                    //cSprites[gridY][gridX] = wall.sprite;
                } else {
                    wall = new Wall(x, y, "Textures/Terrain/grass.png", app);
                }
                lSprites[gridY][gridX] = wall.sprite;
                wall.render();
                guiNode.attachChild(wall.sprite);
            }
        }
        renderItems();
    }
}