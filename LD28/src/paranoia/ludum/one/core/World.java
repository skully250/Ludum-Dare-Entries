package paranoia.ludum.two.core;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import java.util.ArrayList;
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
    public Picture[][] cSprites;
    public ArrayList<Picture> itemList;
    
    public World(SimpleApplication app, AppSettings settings) {
        this.app = app;
        this.settings = settings;
        this.assetManager = app.getAssetManager();
        this.guiNode = app.getGuiNode();
        //These are really unnecessary but ill simplify later
        int gridY = settings.getHeight() / ApplicationMain.GRID_SIZE;
        int gridX = settings.getWidth() / ApplicationMain.GRID_SIZE;
        itemList = new ArrayList<Picture>();
        lSprites = new Picture[gridY][gridX];
        cSprites = new Picture[gridY][gridX];
    }
    
    public void addItems() {
        Picture burger = new Picture();
        Picture tuna = new Picture();
        Picture donut = new Picture();
        Picture fries = new Picture();
        burger.setImage(assetManager, "Textures/Extras/Food/Burger.png", true);
        tuna.setImage(assetManager, "Textures/Extras/Food/canoftuna.png", true);
        donut.setImage(assetManager, "Textures/Extras/Food/donut.png", true);
        fries.setImage(assetManager, "Textures/Extras/Food/fries.png", true);
        itemList.add(burger);
        itemList.add(tuna);
        itemList.add(donut);
        itemList.add(fries);
    }
    
    public void renderEntities() {
        
    }
    
    public void renderItems() {
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            int rando = rand.nextInt(3);
            System.out.println(rando + " is the random number");
            if (rando == 3 || rando == 2) {
                int ix = rand.nextInt(47);
                int iy = rand.nextInt(47);
                System.out.println(ix + " " + iy);
                itemList.get(i).setLocalTranslation(ix * 16, iy * 16, 3);
                System.out.println(itemList.get(i).getLocalTranslation());
                int index = rand.nextInt(itemList.size() - 1);
                guiNode.attachChild(itemList.get(index));
                System.out.println(itemList.get(i) + " added");
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
    }
}