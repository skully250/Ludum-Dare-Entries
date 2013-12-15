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
    
    public boolean renderTextBox = false;
    
    //Position of the player
    public int x, y;
    
    //Pictures for extra things
    public Picture textBox, background, lScreen;
    public Picture[] imageSprites = new Picture[10];
    
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
        player = new Sprite("Textures/Entities/Player.png", "Player", assetManager,
                true, true, 1, 1, 0.0f, "End", "Start");
        npcs[0] = new Sprite("Textures/Entities/Female.png", "Female", assetManager,
                true, true, 1, 1, 0.0f, "End", "Start");
        npcs[0].moveAbsolute(208, 208);
        imageSprites[0] = new Picture();
        imageSprites[0].setImage(assetManager, "Textures/Terrain/dirt.jpg", false);
        World level = new World(this, settings);
        level.generateLevel();
        SpriteLibrary.l_guiNode = guiNode;
        SpriteLibrary library = new SpriteLibrary("Library1", false);
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(player);
        sprites.add(npcs[0]);
        for (int i = 0; i < sprites.size(); i++)
            library.addSprite(sprites.get(i));
        
        engine.addLibrary(library);
        
        background = new Picture("Background");
        terrainNode = new Node("Terrain");
        rootNode.attachChild(terrainNode);
        System.out.println("Grass texture done overlay");
        initKeys();
    }
    
    public void initBackground() {
        for (int ty = 0; ty < settings.getHeight() - 100; ty++) {
            for (int tx = 0; tx < settings.getWidth() - 100; tx++) {
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
        inputManager.addListener(player_listener, "Right", "Left", "Up", "Down", "Talk");
    }
    
    ActionListener player_listener = new ActionListener() {
        
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Right")) {
                x += 8;
            }
            else if (name.equals("Left")) {
                x -= 8;
            }
            else if (name.equals("Up")) {
                y += 8;
            }
            else if (name.equals("Down")) {
                y -= 8;
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
            lScreen.setImage(assetManager, "Textures/Extras/Screens/loadingscreen.png", false);
            lScreen.setWidth(768);
            lScreen.setHeight(600);
            lScreen.setLocalTranslation(-80, -80, 4);
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
        }
        player.moveAbsolute(x, y);
        this.guiViewPort.setClearColor(true);
        this.guiViewPort.setBackgroundColor(ColorRGBA.White);
    }
}