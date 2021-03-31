package fi.tuni.tamk;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class MiniGame implements Screen {
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    Sound correct;
    Sound incorrect;
    BitmapFont font;
    Texture nice;
    boolean itsCorrect = false;
    boolean answerIsGiven = false;
    ArrayList<String> sorrowProblems;
    ArrayList<String> sorrowProblemOne;
    ArrayList<String> sorrowProblemTwo;
    ArrayList<String> sorrowProblemThree;

    ArrayList<String> hateProblems;
    ArrayList<String> hateProblemOne;
    ArrayList<String> hateProblemTwo;
    ArrayList<String> hateProblemThree;

    ArrayList<String> fearProblems;
    ArrayList<String> fearProblemOne;
    ArrayList<String> fearProblemTwo;
    ArrayList<String> fearProblemThree;
    Texture problemBox;
    Texture answerBox;
    Image answerImage;


    public static float problemWidth = 900f;
    public static float problemHeight = 200f;

    public static boolean youAreGoddamnRight = false;

    boolean hate = false;
    boolean sorrow = false;
    boolean fear = false;
    ArrayList<String> problemList;
    ArrayList<String> answerList;
    String problem;
    String ans1;
    String ans2;
    String ans3;
    int pIndex;
    int aIndex;
    int correctXUpperlimit;
    int correctXLowerlimit;
    int correctYUpperlimit;
    int correctYLowerlimit;
    boolean soundIsPlayed = false;
    SpriteBatch batch;
    Main host;
    TowerOfLife theGame;
    boolean hide = false;
    int answerCounter = 0;


    public MiniGame(String e, Main host) {
        batch = host.theGame.hudbatch;
        this.host = host;
        nice = new Texture(Gdx.files.internal("nice.png"));
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 72;
        fontParameter.borderWidth = 1.5f;
        fontParameter.borderColor = Color.LIGHT_GRAY;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
        font.getData().setScale(0.5f, 0.5f);
        correct = Gdx.audio.newSound(Gdx.files.internal("correct.mp3"));
        incorrect = Gdx.audio.newSound(Gdx.files.internal("wrong.mp3"));

        sorrowProblems = new ArrayList<>();
        sorrowProblemOne = new ArrayList<>();
        sorrowProblemTwo = new ArrayList<>();
        sorrowProblemThree = new ArrayList<>();

        hateProblems = new ArrayList<>();
        hateProblemOne = new ArrayList<>();
        hateProblemTwo = new ArrayList<>();
        hateProblemThree = new ArrayList<>();
        fearProblems = new ArrayList<>();
        fearProblemOne = new ArrayList<>();
        fearProblemTwo = new ArrayList<>();
        fearProblemThree = new ArrayList<>();

        problemList = new ArrayList<>();
        answerList = new ArrayList<>();

        fearProblems.add("fearproblem1");
        fearProblems.add("fearproblem2");
        fearProblems.add("fearproblem3");

        fearProblemOne.add("fearproblem1 wrong");
        fearProblemOne.add("fearproblem1 wrong2");
        fearProblemOne.add("correct!");

        fearProblemTwo.add("fearproblem2 wrong");
        fearProblemTwo.add("fearproblem2 wrong2");
        fearProblemTwo.add("correct!");

        fearProblemThree.add("fearproblem3 wrong");
        fearProblemThree.add("fearproblem3 wrong2");
        fearProblemThree.add("correct!");


        sorrowProblems.add("sorrowproblem1");
        sorrowProblems.add("sorrowproblem2");
        sorrowProblems.add("sorrowproblem3");

        sorrowProblemOne.add("sorr.problem1 wrong");
        sorrowProblemOne.add("sorr.problem1 wrong2");
        sorrowProblemOne.add("correct!");

        sorrowProblemTwo.add("sorr.problem2 wrong");
        sorrowProblemTwo.add("sorr.problem2 wrong2");
        sorrowProblemTwo.add("correct!");

        sorrowProblemThree.add("sorr.problem3 wrong");
        sorrowProblemThree.add("sorr.problem3 wrong2");
        sorrowProblemThree.add("correct!");


        hateProblems.add("hateproblem1");
        hateProblems.add("hateproblem2");
        hateProblems.add("hateproblem3");

        hateProblemOne.add("hateproblem 1 wrong");
        hateProblemOne.add("hateproblem 1 wrong2");
        hateProblemOne.add("correct!");

        hateProblemTwo.add("hateproblem 2 wrong");
        hateProblemTwo.add("hateproblem 2 wrong2");
        hateProblemTwo.add("correct!");

        hateProblemThree.add("hateproblem 3 wrong");
        hateProblemThree.add("hateproblem 3 wrong2");
        hateProblemThree.add("correct!");

        problemBox = new Texture(Gdx.files.internal("problem_box.png"));
        answerBox = new Texture(Gdx.files.internal("answer_box.png"));
        answerImage = new Image(answerBox);

        if (e.equals("sorrowBox")) {
            problemList.addAll(sorrowProblems);
        } else if (e.equals("hateBox")) {
            problemList.addAll(hateProblems);
        } else {
            problemList.addAll(fearProblems);
        }

        pIndex = MathUtils.random(0, 2);
        problem = problemList.get(pIndex);

        if (e.equals("sorrowBox")) {
            if (pIndex == 0) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = sorrowProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 420;
                    correctYLowerlimit = 240;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = sorrowProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 660;
                    correctYLowerlimit = 480;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = sorrowProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 900;
                    correctYLowerlimit = 720;
                }

            } else if (pIndex == 1) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = sorrowProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 420;
                    correctYLowerlimit = 240;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = sorrowProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 660;
                    correctYLowerlimit = 480;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = sorrowProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 900;
                    correctYLowerlimit = 720;
                }
            } else {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = sorrowProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 420;
                    correctYLowerlimit = 240;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = sorrowProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 660;
                    correctYLowerlimit = 480;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = sorrowProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 900;
                    correctYLowerlimit = 720;
                }
            }
        } else if (e.equals("fearBox")) {
            if (pIndex == 0) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = fearProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 420;
                    correctYLowerlimit = 240;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = fearProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 660;
                    correctYLowerlimit = 480;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = fearProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 900;
                    correctYLowerlimit = 720;
                }

            } else if (pIndex == 1) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = fearProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 420;
                    correctYLowerlimit = 240;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = fearProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 660;
                    correctYLowerlimit = 480;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = fearProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 900;
                    correctYLowerlimit = 720;
                }
            } else {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = fearProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 420;
                    correctYLowerlimit = 240;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = fearProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 660;
                    correctYLowerlimit = 480;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = fearProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 900;
                    correctYLowerlimit = 720;
                }
            }
        } else {
            if (pIndex == 0) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = hateProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 420;
                    correctYLowerlimit = 240;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = hateProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 660;
                    correctYLowerlimit = 480;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = hateProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 900;
                    correctYLowerlimit = 720;
                }

            } else if (pIndex == 1) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = hateProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 420;
                    correctYLowerlimit = 240;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = hateProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 660;
                    correctYLowerlimit = 480;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = hateProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 900;
                    correctYLowerlimit = 720;
                }
            } else {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = hateProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 420;
                    correctYLowerlimit = 240;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = hateProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 660;
                    correctYLowerlimit = 480;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = hateProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 800;
                    correctYUpperlimit = 900;
                    correctYLowerlimit = 720;
                }
            }
        }

    }

    public void render(float delta) {

        batch.begin();
        if (!hide) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.draw(problemBox, 0, TowerOfLife.WORLD_HEIGHT * 100 - 300, problemWidth, problemHeight + 200);
            font.draw(batch, problem, 10, TowerOfLife.WORLD_HEIGHT * 100 - 100);

            batch.draw(answerBox, 0, 900, 800, 300);
            font.draw(batch, ans1, 10, 1100);

            batch.draw(answerBox, 0, 500, 800, 300);
            font.draw(batch, ans2, 10, 700);

            batch.draw(answerBox, 0, 100, 800, 300);
            font.draw(batch, ans3, 10, 300);
            if (answerIsGiven) {
                answerCounter++;
                if (itsCorrect) {
                    batch.draw(nice, 280, 800, 400, 150);
                    batch.draw(nice, 100, 1000, 300, 120);
                    batch.draw(nice, 550, 1000, 300, 120);
                    batch.draw(nice, 150, 600, 300, 120);
                    batch.draw(nice, 500, 600, 300, 120);
                }
                if (answerCounter > 60) {
                    host.theGame.minigameStart = false;
                    host.theGame.miniGameCounter = 0;
                    host.changeNow = true;
                }
            }

            choose();
        }

        batch.end();
    }

    public void choose() {
        if (Gdx.input.isTouched()) {

            Vector3 touchPoint = new Vector3();
            TowerOfLife.hudcamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            Gdx.app.log("x", "" + touchPoint.x);
            Gdx.app.log("y", "" + touchPoint.y);



            //answer 1
            if ((Gdx.input.getX() > 0 && Gdx.input.getX() < 800) && (Gdx.input.getY() > 240 && Gdx.input.getY() < 420)) {

                isAnswerRight(Gdx.input.getX(), Gdx.input.getY());

                if (Gdx.app.getType() != Application.ApplicationType.Android) {
                    answerIsGiven = true;

                }




            }  else if ((Gdx.input.getX() > 0 && Gdx.input.getX() < 800) && (Gdx.input.getY() > 720 && Gdx.input.getY() < 900)) {
                isAnswerRight(Gdx.input.getX(), Gdx.input.getY());

                if (Gdx.app.getType() != Application.ApplicationType.Android) {
                    answerIsGiven = true;

                }



            }/*answer2*/  else if ((Gdx.input.getX() > 0 && Gdx.input.getX() < 800) && (Gdx.input.getY() > 480 && Gdx.input.getY() < 660)) {
                isAnswerRight(Gdx.input.getX(), Gdx.input.getY());

                if (Gdx.app.getType() != Application.ApplicationType.Android) {
                    answerIsGiven = true;

                }



            }

        }
    }


    public void isAnswerRight(int x, int y) {
        if ((x < correctXUpperlimit && x > correctXLowerlimit) && (y < correctYUpperlimit && y > correctYLowerlimit)) {
            if (!soundIsPlayed) {
                correct.play();
                itsCorrect = true;
                host.theGame.score += 2;
                host.theGame.gongrats = true;
                soundIsPlayed = true;
            }

        }  else {
            if (!soundIsPlayed) {
                incorrect.play();
                soundIsPlayed = true;
            }


        }
    }

    @Override
    public void show() {

    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        correct.dispose();
        incorrect.dispose();
        font.dispose();
        batch.dispose();
    }
}