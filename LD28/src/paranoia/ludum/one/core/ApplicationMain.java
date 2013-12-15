package paranoia.ludum.one.core;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import paranoia.ludum.one.lib.Sprite;
import paranoia.ludum.one.lib.SpriteEngine;
import paranoia.ludum.one.lib.SpriteLibrary;

/**
 *
 * @author Skully
 */
public class ApplicationMain extends SimpleApplication {
    
    //Arrays for pretty much everything in the game
    public Sprite[] face = new Sprite[5];
    public Description[] description = new Description[5];
    public Vector2f[] positionS = new Vector2f[5];
    public Vector2f[] positionE = new Vector2f[5];
    
    public Picture textBox;
    
    //The text for drawing the descriptions
    BitmapText text;

    //Audio nodes for stuff
    AudioNode Main_Theme;
    AudioNode Right, Wrong;
    
    private int guesses = 1;
    public int page = 0;
    static ApplicationMain app;
    
    /*public static void main(String[] args) {
        app = new ApplicationMain();
        AppSettings settings = new AppSettings(false);
        app.setShowSettings(false);
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        settings.setResolution(1024, 768);
        app.start();
    }*/
    
    static SpriteEngine engine = new SpriteEngine();
    
    @Override
    public void simpleInitApp() {
        text = new BitmapText(guiFont, false);
        flyCam.setEnabled(false);
        positionS[0] = new Vector2f(0, 0);
        positionE[0] = new Vector2f(254, 254);
        positionS[1] = new Vector2f(0, settings.getHeight());
        positionE[1] = new Vector2f(307, 270);
        
        textBox = new Picture("Text Box");
        textBox.setImage(assetManager, "Textures/SHITTY text BOX.png", false);

        Main_Theme = new AudioNode(assetManager, "Sounds/Main_Theme.ogg", false);
        Right = new AudioNode(assetManager, "Sounds/Right.wav", false);
        Wrong = new AudioNode(assetManager, "Sounds/Wrong.wav", false);
        Main_Theme.setLooping(true);
        Main_Theme.play();
        
        face[0] = new Sprite("Textures/face1.jpg", "Face1", assetManager, true, false, 1, 1, 0.0f, "End", "Start");
        face[1] = new Sprite("Textures/face2.jpg", "Face2", assetManager, true, false, 1, 1, 0.0f, "End", "Start");
        description[0] = new Description("He is a murderer", face[0]);
        description[1] = new Description("She is not", face[1]);
        
        face[0].setOrder(1); face[1].setOrder(0);
        
        //Rendering the sprite here
        SpriteLibrary.l_guiNode = guiNode;
        SpriteLibrary library = new SpriteLibrary("Library1", false);
        //For keeping the sprites in an array for rendering
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(face[0]); sprites.add(face[1]);
        for (int i = 0; i < sprites.size(); i++)
            library.addSprite(sprites.get(i));
        
        engine.addLibrary(library);
        //Y-0 = bottom
        face[0].moveAbsolute(0, 0);
        face[1].moveAbsolute(0, 0);
        
        initKeys();
    }
    
    public void initKeys() {
        inputManager.addMapping("Info", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("guess", new KeyTrigger(KeyInput.KEY_0));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(mouseListener, "Info", "guess");
        inputManager.addListener(pageListener, "Left", "Right");
    }
    
    ActionListener pageListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Right")) {
                if (page < 5) {
                page += 1;
                } else {
                    page = 0;
                }
            }
            
            else if (name.equals("Left")) {
                if (page > 0) {
                page -= 1;
                } else {
                    page = 4;
                }
            }
        }
    };
    
    ActionListener mouseListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Info")) {
                Vector2f MPosition = inputManager.getCursorPosition();
                System.out.println("Position taken");
                face[page].moveAbsolute(settings.getWidth() / 2, settings.getHeight() / 2);
            }
            else if (name.equals("guess")) {
                guesses = 0;
            }
        }
    };
    
    public void writeDescription(Description desc){
        String message = desc.desc;
        text.setText(message);
        text.setColor(ColorRGBA.White);
        text.setLocalTranslation(0, settings.getHeight() / 4, 0);
        text.setSize(12f);
        guiNode.attachChild(text);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        if (guesses == 0) {
            app.stop();
        }
    }
}
