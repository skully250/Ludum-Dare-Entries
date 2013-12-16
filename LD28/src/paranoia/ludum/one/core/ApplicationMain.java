package paranoia.ludum.two.core;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import java.util.Random;
import paranoia.ludum.two.lib.Sprite;
import paranoia.ludum.two.lib.SpriteEngine;
import paranoia.ludum.two.lib.SpriteLibrary;

/**
 *
 * @author Skully
 */
public class ApplicationMain extends SimpleApplication {
    
    static ApplicationMain app;
    static AppSettings settings;
    
    //Sprites for NPC's and player
    public Sprite player;
    public Sprite[] npcs = new Sprite[10];
    public Picture inventory;
    
    public boolean renderTextBox = false;
    public boolean loading = true;
    
    //Position of the player
    public int x, y;
    public int npx = 208, npy = 208;
    
    public static final int GRID_SIZE = 16;
    
    Random rand = new Random();
    
    //Pictures for extra things
    public Picture textBox, background, lScreen;
    public Picture[] imageSprites = new Picture[10];
    
    public World level;
    
    public BitmapText text, hudText;
    
    public Node terrainNode;
    
    public float loadTime = 3f;
    float timer = 0;
    
    public static ApplicationMain getInstance() {
        return app;
    }
    
    public static void main(String[] args) {
        app = new ApplicationMain();
        settings = new AppSettings(false);
        settings.setResolution(1024, 768);
        app.setShowSettings(false);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        app.start();
    }
    
    static SpriteEngine engine = new SpriteEngine();
    
    @Override
    public void simpleInitApp() {
        renderLoadScreen();
        flyCam.setEnabled(false);
        player = new Sprite("Textures/Entities/Player.png", "Player", assetManager, false, true, 1, 1, 0.0f, "End", "Start");
        npcs[0] = new Sprite("Textures/Entities/Female.png", "Female", assetManager, false, true, 1, 1, 0.0f, "End", "Start");
        npcs[0].moveAbsolute(npx, npy);
        imageSprites[0] = new Picture();
        imageSprites[0].setImage(assetManager, "Textures/Terrain/dirt.jpg", false);
        level = new World(this, settings);
        level.addItems();
        level.generateLevel();
        SpriteLibrary.l_guiNode = guiNode;
        SpriteLibrary library = new SpriteLibrary("Library1", false);
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(player);
        sprites.add(npcs[0]);
        for (Sprite sprite : sprites)
            library.addSprite(sprite);
        
        player.setOrder(4);
        npcs[0].setOrder(4);
        
        engine.addLibrary(library);
        
        background = new Picture("Background");
        terrainNode = new Node("Terrain");
        rootNode.attachChild(terrainNode);
        System.out.println("Grass texture done overlay");
        initKeys();
    }
    
    public void grabItem(int x, int y) {
        if (level.fSprites[y][x] != null) {
            Picture item = level.fSprites[y][x];
            guiNode.detachChild(level.fSprites[y][x]);
            inventory = item;
        }
    }
    
    public void initBackground() {
        for (int ty = 0; ty < settings.getHeight() / GRID_SIZE; ty++) {
            for (int tx = 0; tx < settings.getWidth() / GRID_SIZE; tx++) {
                background.setImage(assetManager, "Textures/grass.jpg", true);
                background.setPosition(x, y);
                terrainNode.attachChild(background);
            }
        }
    }
    
    public void writeText(String message) {
        text = new BitmapText(guiFont, false);
        text.setColor(ColorRGBA.White);
        text.setSize(12f);
        text.setText(message);
    }
    
    public void initKeys() {
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("Talk", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("Grab", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("Drop", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(player_listener, "Right", "Left", "Up", "Down", "Grab", "Drop");
    }
    
    ActionListener player_listener = new ActionListener() {
        
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Right")) {
                if (level.canMove(x + 8, y))
                x += 8;
            }
            else if (name.equals("Left")) {
                if (level.canMove(x - 8, y))
                x -= 8;
            }
            else if (name.equals("Up")) {
                if (level.canMove(x, y + 8))
                y += 8;
            }
            else if (name.equals("Down")) {
                if (level.canMove(x, y - 8))
                y -= 8;
            }
            else if (name.equals("Grab")) {
                if (isPressed) {
                    grabItem(x/GRID_SIZE, y/GRID_SIZE);
                    System.out.println(x/GRID_SIZE + " " + y/GRID_SIZE);
                }
            }
        }
        
    };
    
    public void renderTextBox(String message) {
        textBox = new Picture("TextBox");
        hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.White);                             // font color
        hudText.setText(message);                                       // the text
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 0); // position
        hudText.setLocalTranslation(0, 100, 2);
        
        textBox.setImage(assetManager, "Textures/Menu/TextBox.png", false);
        textBox.setWidth(settings.getWidth());
        textBox.setHeight(100);
        textBox.move(0, 0, 1);
        
        guiNode.attachChild(textBox);
        guiNode.attachChild(hudText);
    }
    
    public void renderLoadScreen() {
        lScreen = new Picture("Loading Screen");
        lScreen.setImage(assetManager, "Textures/Menu/loadingscreen.png", false);
        lScreen.setWidth(768);
        lScreen.setHeight(600);
        lScreen.setLocalTranslation(-80, -80, 5);
        guiNode.attachChild(lScreen);
    }
    
    public void clearLoadingScreen() {
        lScreen.setWidth(0);
        lScreen.setHeight(0);
        guiNode.detachChild(lScreen);
    }
    
    public void clearTextBox() {
        if (guiNode.hasChild(textBox)) {
            guiNode.detachChild(textBox);
            textBox.setTexture(assetManager, null, false);
        }
        if (guiNode.hasChild(hudText)) {
            guiNode.detachChild(hudText);
            hudText.setText(" ");
        }
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        timer += tpf;
        if (timer > loadTime) {
            clearLoadingScreen();
            loading = false;
        }
        if (!loading) {
            if (timer > loadTime) {
                int nnpx = rand.nextInt(2);
                switch(nnpx) {
                    case 1 :
                        if (level.canMove(npx + 16, npy))
                        npx += 16;
                        break;
                    case 2 :
                        if (level.canMove(npx - 16, npy))
                        npx -= 16;
                        break;
                }
                int nnpy = rand.nextInt(2);
                switch(nnpy) {
                    case 1 :
                        if (level.canMove(npx, npy + 16))
                        npy += 16;
                        break;
                    case 2 :
                        if (level.canMove(npx, npy - 16))
                        npy -= 16;
                        break;
                }
                timer = 0;
            }
            if (x < 0 || npx < 0) {
                x = settings.getWidth();
            } else if (x > settings.getWidth()) {
                x = 0;
            }
            if (y < 0 || npy < 0) {
                y = settings.getHeight();
            } else if (y > settings.getHeight()) {
                y = 0;
            }
            player.moveAbsolute(x, y);
            npcs[0].moveAbsolute(npx, npy);
            this.guiViewPort.setClearColor(true);
            this.guiViewPort.setBackgroundColor(ColorRGBA.White);
        }
    }
}