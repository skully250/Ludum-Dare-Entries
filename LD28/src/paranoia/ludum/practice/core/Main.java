package paranoia.ludum.practice.core;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector2f;
import com.jme3.system.AppSettings;
import java.util.ArrayList;
import paranoia.ludum.practice.entity.EntityPlayer;
import paranoia.ludum.practice.world.level.LevelCave;
import paranoia.ludum.practice.world.level.LevelOverworld;
import paranoia.ludum.one.lib.Sprite;
import paranoia.ludum.one.lib.SpriteEngine;
import paranoia.ludum.one.lib.SpriteLibrary;

public class Main extends SimpleApplication {
    
    static Main app;
    public int x = 0, y = 0;
    public Vector2f position;
    
    public SpriteLibrary library;
    public Sprite sprite, grass;
    public Sprite Player_Left, Player_Right, Player_Up, Player_Down;
    
    public static ArrayList<Sprite> sprites;
    public boolean left, right, up, down;
    
    public LevelOverworld overworld;
    public LevelCave cave;
    
    /*public static void main(String[] args) {
        app = new Main();
        AppSettings settings = new AppSettings(false);
        app.setShowSettings(false);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        settings.setResolution(800, 600);
        app.start();
    }*/
    
    static SpriteEngine engine = new SpriteEngine();
    
    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        Player_Left = new Sprite("Textures/Player_Left.png", "Player_Left", assetManager, true, true, 4, 1, 0.515f, "Loop", "Start");
        Player_Right = new Sprite("Textures/Player_Right.png", "Player_Right", assetManager, true, true, 4, 1, 0.515f, "Loop", "Start");
        Player_Up = new Sprite("Textures/Player_Up.png", "Player_Right", assetManager, true, true, 4, 1, 0.515f, "Loop", "Start");
        Player_Down = new Sprite("Textures/Player_Down.png", "Player_Right", assetManager, true, true, 4, 1, 0.515f, "Loop", "Start");
        sprite = new Sprite("Textures/testAnim.png", "Sprite 1", assetManager, true, true, 4, 4, 0.515f, "Loop", "Start");
        grass = new Sprite("Textures/background.png", "Grass Sprite", assetManager, false, false, 1, 1, 0.0f, "GEnd", "GStart");
        SpriteLibrary.l_guiNode = guiNode;
        library = new SpriteLibrary("Library 1", false);
        sprites = new ArrayList<Sprite>();
        sprites.add(sprite);
        sprites.add(grass);
        overworld = new LevelOverworld(sprites, library);
        //EntityPlayer player = new EntityPlayer(sprite, overworld);
        //sprite.equals(player.getSprite());
        engine.addLibrary(library);
        sprite.setOrder(1);
        // 0 is bottom
        grass.moveAbsolute(0, -10);
        initKeys();
    }
    
    public void initKeys() {
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(movement, "Left", "Right", "Up", "Down");
    }
    
    public ActionListener movement = new ActionListener() {
        
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Left")) {
                x -= 10;
            }
            else if (name.equals("Right")) {
                x += 10;
            }
            else if (name.equals("Up")) {
                y += 10;
            }
            else if (name.equals("Down")) {
                y -= 10;
            }
        }
        
    };
    
    @Override
    public void simpleUpdate(float tpf) {
        engine.update(tpf);
        sprite.moveAbsolute(x, y);
    }
}