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
    Texture answerBox;

    public static float problemWidth = 900f;
    public static float problemHeight = 200f;

    public boolean youAreGoddamnRight;

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
        // font.getData().setScale(0.02f, 0.02f);

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
        answerBox = new Texture(Gdx.files.internal("answer_box.png"));


    }

    public void draw(SpriteBatch b) {
        b.draw(problemBox, 0, TowerOfLife.WORLD_HEIGHT * 100 - 200, problemWidth, problemHeight);
        font.draw(b, "Some problem", 250, TowerOfLife.WORLD_HEIGHT * 100 - 100);

        b.draw(answerBox, 0, TowerOfLife.WORLD_HEIGHT * 100 - 400, 400, 150);
        font.draw(b, "Answer1" , 10, TowerOfLife.WORLD_HEIGHT * 100 - 300);

        b.draw(answerBox, 500, TowerOfLife.WORLD_HEIGHT * 100 - 400, 400, 150);
        font.draw(b, "Answer2" , 550, TowerOfLife.WORLD_HEIGHT * 100 - 300);

        b.draw(answerBox, 250, TowerOfLife.WORLD_HEIGHT * 100 - 600, 400, 150);
        font.draw(b, "Answer3" , 300, TowerOfLife.WORLD_HEIGHT * 100 - 500);
    }
}
