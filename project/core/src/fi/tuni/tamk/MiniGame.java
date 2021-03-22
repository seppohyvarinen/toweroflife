package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;

public class MiniGame {
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    ArrayList<String> sorrowProblems;
    ArrayList<String> sorrowWrongAnswers;
    ArrayList<String> sorrowRightAnswers;
    ArrayList<String> hateProblems;
    ArrayList<String> hateWrongAnswers;
    ArrayList<String> hateRightAnswers;
    ArrayList<String> fearProblems;
    ArrayList<String> fearWrongAnswers;
    ArrayList<String> fearRightAnswers;
    Texture problemBox;

    public static float problemWidth = 5f;
    public static float problemHeight = 2.2f;

    boolean hate = false;
    boolean sorrow = false;
    boolean fear = false;



    public MiniGame(String e) {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 72;
        fontParameter.borderWidth = 1.5f;
        fontParameter.borderColor = Color.LIGHT_GRAY;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
        font.getData().setScale(0.02f, 0.02f);

        sorrowProblems = new ArrayList<>();
        sorrowWrongAnswers = new ArrayList<>();
        sorrowRightAnswers = new ArrayList<>();
        hateProblems = new ArrayList<>();
        hateWrongAnswers = new ArrayList<>();
        hateRightAnswers = new ArrayList<>();
        fearProblems = new ArrayList<>();
        fearWrongAnswers = new ArrayList<>();
        fearRightAnswers = new ArrayList<>();

        sorrowProblems.add("Some sorrow related problem");
        sorrowWrongAnswers.add("wrong answer");
        sorrowRightAnswers.add("right answer");

        hateProblems.add("Some sorrow related problem");
        hateWrongAnswers.add("wrong answer");
        hateRightAnswers.add("right answer");
        problemBox = new Texture(Gdx.files.internal("problem_box.png"));


    }

    public void draw(SpriteBatch b) {
        b.draw(problemBox, TowerOfLife.camera.position.x - 2.3f, TowerOfLife.camera.position.y + 4f, problemWidth, problemHeight);
        font.draw(b, "Some problem", TowerOfLife.camera.position.x - 2.3f, TowerOfLife.camera.position.y + 4f);

    }
}
