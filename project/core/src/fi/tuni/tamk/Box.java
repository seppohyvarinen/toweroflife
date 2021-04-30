package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;


/**
 * Box class creates the boxes used by the Tower Of Life class.
 * <p>
 * There are different types of Boxes used in the game. The types of Boxes are determined in the constructor. The boxes all have a Body.
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */

public class Box {

    Body body;
    Texture bodyTexture;
    public static float boxWidth = 4 / 3f;
    public static float boxHeight = 2 / 3f;
    public static String userData;
    boolean hasBody = false;
    Sound fearSound;
    Sound angerSound;
    Sound sorrowSound;
    Sound hateSound;
    Sound joySound;
    Sound aweSound;
    Sound loveSound;

    private boolean drop = false;

    /**
     * Constructor for the Box Class. Determines the type of Box and initializes all the possible Sounds the Box can use and it's Texture.
     *
     * @param t is the Texture the Box uses when it's rendered.
     * @param d is the String value that is inserted as userdata to the Body of the Box to control the collisions.
     */

    public Box(Texture t, String d) {
        bodyTexture = t;
        userData = d;
        fearSound = Gdx.audio.newSound(Gdx.files.internal("fear.mp3"));
        angerSound = Gdx.audio.newSound(Gdx.files.internal("angerb.mp3"));
        sorrowSound = Gdx.audio.newSound(Gdx.files.internal("sorrow.mp3"));
        hateSound = Gdx.audio.newSound(Gdx.files.internal("anger.mp3"));
        joySound = Gdx.audio.newSound(Gdx.files.internal("joy.mp3"));
        aweSound = Gdx.audio.newSound(Gdx.files.internal("awe.mp3"));
        loveSound = Gdx.audio.newSound(Gdx.files.internal("love.mp3"));
    }

    /**
     * Method that is called in the Tower Of Life classes render() method to draw the Box.
     *
     * @param b is Spritebatch that handles the drawing of the Boxes Texture.
     */

    public void draw(SpriteBatch b) {

        if (!drop) {
            b.draw(bodyTexture, TowerOfLife.realX, TowerOfLife.realY, boxWidth * 2, boxHeight * 2);
        } else {
            b.draw(bodyTexture, body.getPosition().x - boxWidth, body.getPosition().y - boxHeight, boxWidth, boxHeight, boxWidth * 2, boxHeight * 2, 1.01f, 1.01f, body.getTransform().getRotation() * MathUtils.radiansToDegrees, 0, 0, bodyTexture.getWidth(), bodyTexture.getHeight(), false, false);
        }
    }

    /**
     * Method that creates the Body for the box. The type of Body (or the Body's attributes) depend on the type of the Box.
     * <p>
     * To the user this method acts as the boxes dropper, when in fact the Body is only created after this method is called and so the
     * Box is affected by the Box2dPhysics.
     */

    public void dropIt() {
        drop = true;
        float bouncePlus = 0.25f;
        if (TowerOfLife.bounceMultiplier == 1 || TowerOfLife.bounceMultiplier > 7) {
            bouncePlus = 0.18f;
        }
        if (bodyTexture == Resources.joy) {
            body = Util.createJoyBox(TowerOfLife.realX + boxWidth / 2 + 0.5f, TowerOfLife.realY + boxHeight / 2 + 0.5f, boxWidth, boxHeight, TowerOfLife.bounceMultiplier * 0.07f + bouncePlus);
        } else if (bodyTexture == Resources.awe) {
            body = Util.createAweBox(TowerOfLife.realX + boxWidth / 2 + 0.5f, TowerOfLife.realY + boxHeight / 2 + 0.5f, boxWidth, boxHeight);

        } else if (bodyTexture == Resources.hate) {
            body = Util.createAweBox(TowerOfLife.realX + boxWidth / 2 + 0.5f, TowerOfLife.realY + boxHeight / 2 + 0.5f, boxWidth, boxHeight);

        } else if (bodyTexture == Resources.fear) {
            body = Util.createFearBox(TowerOfLife.realX + boxWidth / 2 + 0.5f, TowerOfLife.realY + boxHeight / 2 + 0.5f, boxWidth, boxHeight);

        } else {
            body = Util.createBox(TowerOfLife.realX + boxWidth / 2 + 0.5f, TowerOfLife.realY + boxHeight / 2 + 0.5f, boxWidth, boxHeight);
        }
        body.setUserData(userData);
        hasBody = true;
    }

    /**
     * Method for acquiring the information whether the box is dropped or not.
     *
     * @return returns the boolean value whether the Box is dropped or not.
     */
    public boolean getDropState() {
        return this.drop;
    }

    /**
     * Method for making a sound when the box collides in the world.
     * <p>
     * The sound played depends on the type of the Box.
     */

    public void makeSound() {
        if (bodyTexture == Resources.joy) {
            joySound.play();
        } else if (bodyTexture == Resources.fear) {
            fearSound.play();
        } else if (bodyTexture == Resources.awe) {
            aweSound.play();
        } else if (bodyTexture == Resources.hate) {
            hateSound.play();
        } else if (bodyTexture == Resources.sorrow) {
            sorrowSound.play();
        } else if (bodyTexture == Resources.anger) {
            angerSound.play();
        } else if (bodyTexture == Resources.love) {
            loveSound.play();
        }
    }

    /**
     * Method that disposes of the Textures and Sounds used by the Box.
     */

    public void dispose() {
        bodyTexture.dispose();
        fearSound.dispose();
        sorrowSound.dispose();
        angerSound.dispose();
        hateSound.dispose();
        joySound.dispose();
        aweSound.dispose();
        loveSound.dispose();
    }
}
