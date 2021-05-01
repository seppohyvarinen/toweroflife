package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.Locale;

public class Resources {

    static Music menuBgm;
    static Sound tap;
    static Sound startGame;

    // Backdropes
    static Texture mainMenuBg;
    static Texture menuBg;
    static Texture backdropGrass;
    static Texture backdrop1;
    static Texture backdrop2;
    static Texture backdrop3;
    static Texture backdrop4;
    static Texture backdrop5;
    static Texture backdrop6;
    static Texture backdrop7;
    static Texture backdrop8;
    static Texture backdrop9;
    static Texture backdrop10;
    static Texture backdrop11;
    static Texture backdrop12;
    static Texture backdrop13;
    static Texture backdrop14;
    static Texture backdrop15;
    static Texture backdrop16;
    static Texture backdrop17;
    static Texture backdrop18;
    static Texture backdrop19;
    static Texture backdrop20;

    static Texture cloud0;
    static Texture cloud1;
    static Texture cloud2;
    static Texture cloud3;
    static ArrayList<Texture> clouds;


    // Boxes
    static Texture anger;
    static Texture awe;
    static Texture fear;
    static Texture hate;
    static Texture joy;
    static Texture love;
    static Texture sorrow;
    static ArrayList<Texture> positive;
    static ArrayList<Texture> negative;

    // MiniGame
    static Texture nice;
    static Texture minigameBg;
    static Texture problemBox;
    static Texture answerBox;
    static Texture answerBoxRight;
    static Texture answerBoxWrong;

    //Sounds & Music


    static Sound correct;
    static Sound incorrect;

    static Music hateM;
    static Music sorrowM;
    static Music angerM;
    static Music fearM;

    // Fonts
    static BitmapFont font;
    static BitmapFont smallFont;
    static BitmapFont smallRedFont;
    static FreeTypeFontGenerator fontGenerator;
    static FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    static FreeTypeFontGenerator.FreeTypeFontParameter smallFontParameter;
    static FreeTypeFontGenerator.FreeTypeFontParameter smallRedFontParameter;
    static FreeTypeFontGenerator minigameFontGenerator;
    static FreeTypeFontGenerator.FreeTypeFontParameter minigameFontParameter;
    static BitmapFont minigameFont;


    public Resources() {
        menuBgm = Gdx.audio.newMusic(Gdx.files.internal("menuehka.mp3"));
        tap = Gdx.audio.newSound(Gdx.files.internal("menutap.mp3"));
        startGame = Gdx.audio.newSound(Gdx.files.internal("startgame.mp3"));
        menuBg = new Texture(Gdx.files.internal("menuBackground.png"));
        mainMenuBg = new Texture(Gdx.files.internal("mainMenuBackground.png"));

        //Backdropes
        backdropGrass = new Texture(Gdx.files.internal("grass.png"));
        backdrop1 = new Texture(Gdx.files.internal("backdrop1.png"));
        backdrop2 = new Texture(Gdx.files.internal("backdrop2.png"));
        backdrop3 = new Texture(Gdx.files.internal("backdrop3.png"));
        backdrop4 = new Texture(Gdx.files.internal("backdrop4.png"));
        backdrop5 = new Texture(Gdx.files.internal("backdrop5.png"));
        backdrop6 = new Texture(Gdx.files.internal("backdrop6.png"));
        backdrop7 = new Texture(Gdx.files.internal("backdrop7.png"));
        backdrop8 = new Texture(Gdx.files.internal("backdrop8.png"));
        backdrop9 = new Texture(Gdx.files.internal("backdrop9.png"));
        backdrop10 = new Texture(Gdx.files.internal("backdrop10.png"));
        backdrop11 = new Texture(Gdx.files.internal("backdrop11.png"));
        backdrop12 = new Texture(Gdx.files.internal("backdrop12.png"));
        backdrop13 = new Texture(Gdx.files.internal("backdrop13.png"));
        backdrop14 = new Texture(Gdx.files.internal("backdrop14.png"));
        backdrop15 = new Texture(Gdx.files.internal("backdrop15.png"));
        backdrop16 = new Texture(Gdx.files.internal("backdrop16.png"));
        backdrop17 = new Texture(Gdx.files.internal("backdrop17.png"));
        backdrop18 = new Texture(Gdx.files.internal("backdrop18.png"));
        backdrop19 = new Texture(Gdx.files.internal("backdrop19.png"));
        backdrop20 = new Texture(Gdx.files.internal("backdrop20.png"));

        clouds = new ArrayList<>();
        cloud3 = new Texture(Gdx.files.internal("cloud3.png"));
        cloud2 = new Texture(Gdx.files.internal("cloud2.png"));
        cloud1 = new Texture(Gdx.files.internal("cloud1.png"));
        cloud0 = new Texture(Gdx.files.internal("cloud0.png"));
        clouds.add(cloud0);
        clouds.add(cloud1);
        clouds.add(cloud2);
        clouds.add(cloud3);

        positive = new ArrayList<>();
        negative = new ArrayList<>();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 140;
        fontParameter.borderWidth = 4f;
        fontParameter.borderColor = Color.GRAY;
        fontParameter.color = Color.YELLOW;

        smallFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        smallFontParameter.size = 72;
        smallFontParameter.borderWidth = 4f;
        smallFontParameter.borderColor = Color.GRAY;
        smallFontParameter.color = Color.YELLOW;

        smallRedFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        smallRedFontParameter.size = 72;
        smallRedFontParameter.borderWidth = 4f;
        smallRedFontParameter.borderColor = Color.BLACK;
        smallRedFontParameter.color = Color.RED;

        font = fontGenerator.generateFont(fontParameter);
        smallFont = fontGenerator.generateFont(smallFontParameter);
        smallRedFont = fontGenerator.generateFont(smallRedFontParameter);

        minigameFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        minigameFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        minigameFontParameter.size = 72;
        minigameFontParameter.borderWidth = 1.5f;
        minigameFontParameter.borderColor = Color.LIGHT_GRAY;
        minigameFontParameter.color = Color.WHITE;
        minigameFont = minigameFontGenerator.generateFont(minigameFontParameter);



        //Boxes
        if (Main.locale.equals(new Locale("fi", "FI"))) {
            awe = new Texture(Gdx.files.internal("em_awe_fi.png"));
            anger = new Texture(Gdx.files.internal("em_anger_fi.png"));
            fear = new Texture(Gdx.files.internal("em_fear_fi.png"));
            hate = new Texture(Gdx.files.internal("em_hate_fi.png"));
            joy = new Texture(Gdx.files.internal("em_joy_fi.png"));
            love = new Texture(Gdx.files.internal("em_love_fi.png"));
            sorrow = new Texture(Gdx.files.internal("em_sorrow_fi.png"));
            positive.add(awe);
            positive.add(joy);
            positive.add(love);
            negative.add(anger);
            negative.add(fear);
            negative.add(hate);
            negative.add(sorrow);
        } else {
            anger = new Texture(Gdx.files.internal("em_anger.png"));
            awe = new Texture(Gdx.files.internal("em_awe.png"));
            fear = new Texture(Gdx.files.internal("em_fear.png"));
            hate = new Texture(Gdx.files.internal("em_hate.png"));
            joy = new Texture(Gdx.files.internal("em_joy.png"));
            love = new Texture(Gdx.files.internal("em_love.png"));
            sorrow = new Texture(Gdx.files.internal("em_sorrow.png"));
            positive.add(awe);
            positive.add(joy);
            positive.add(love);
            negative.add(anger);
            negative.add(fear);
            negative.add(hate);
            negative.add(sorrow);
        }

        // MiniGame
        minigameBg = new Texture(Gdx.files.internal("minigame_bg.png"));
        nice = new Texture(Gdx.files.internal("nice.png"));

        angerM = Gdx.audio.newMusic(Gdx.files.internal("angermusic.mp3"));
        hateM = Gdx.audio.newMusic(Gdx.files.internal("hatemusic.mp3"));
        sorrowM = Gdx.audio.newMusic(Gdx.files.internal("sorrowmusic.mp3"));
        fearM = Gdx.audio.newMusic(Gdx.files.internal("fearmusic.mp3"));

        correct = Gdx.audio.newSound(Gdx.files.internal("correcto.mp3"));
        incorrect = Gdx.audio.newSound(Gdx.files.internal("wrongo.mp3"));

        problemBox = new Texture(Gdx.files.internal("problem_box.png"));
        answerBox = new Texture(Gdx.files.internal("answer_box.png"));
        answerBoxRight = new Texture(Gdx.files.internal("answer_box_right.png"));
        answerBoxWrong = new Texture(Gdx.files.internal("answer_box_wrong.png"));





    }

    public static void changeLanguage() {
        if (Main.locale.equals(new Locale("fi", "FI"))) {
            positive.clear();
            negative.clear();
            awe = new Texture(Gdx.files.internal("em_awe_fi.png"));
            anger = new Texture(Gdx.files.internal("em_anger_fi.png"));
            fear = new Texture(Gdx.files.internal("em_fear_fi.png"));
            hate = new Texture(Gdx.files.internal("em_hate_fi.png"));
            joy = new Texture(Gdx.files.internal("em_joy_fi.png"));
            love = new Texture(Gdx.files.internal("em_love_fi.png"));
            sorrow = new Texture(Gdx.files.internal("em_sorrow_fi.png"));
            positive.add(awe);
            positive.add(joy);
            positive.add(love);
            negative.add(anger);
            negative.add(fear);
            negative.add(hate);
            negative.add(sorrow);
        } else {
            positive.clear();
            negative.clear();
            anger = new Texture(Gdx.files.internal("em_anger.png"));
            awe = new Texture(Gdx.files.internal("em_awe.png"));
            fear = new Texture(Gdx.files.internal("em_fear.png"));
            hate = new Texture(Gdx.files.internal("em_hate.png"));
            joy = new Texture(Gdx.files.internal("em_joy.png"));
            love = new Texture(Gdx.files.internal("em_love.png"));
            sorrow = new Texture(Gdx.files.internal("em_sorrow.png"));
            positive.add(awe);
            positive.add(joy);
            positive.add(love);
            negative.add(anger);
            negative.add(fear);
            negative.add(hate);
            negative.add(sorrow);
        }
    }
}
