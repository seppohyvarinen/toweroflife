package fi.tuni.tamk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import static fi.tuni.tamk.Resources.angerM;
import static fi.tuni.tamk.Resources.answerBox;
import static fi.tuni.tamk.Resources.answerBoxRight;
import static fi.tuni.tamk.Resources.correct;
import static fi.tuni.tamk.Resources.fearM;
import static fi.tuni.tamk.Resources.hateM;
import static fi.tuni.tamk.Resources.incorrect;
import static fi.tuni.tamk.Resources.minigameBg;
import static fi.tuni.tamk.Resources.nice;
import static fi.tuni.tamk.Resources.problemBox;
import static fi.tuni.tamk.Resources.sorrowM;


/**
 * Minigame class handles the minigame questions and sets different variables in the Tower Of Life class according how player answers the questions.
 * <p>
 * Minigame questions are divided into 3 different types. The type of question depends on which block has been dropped in the Tower Of Life class.
 * The game is programmed so that the same question doesn't appear again until all the questions have been answered in the said category.
 * The questions are answered by tapping the screen. The minigame has assigned touch areas in the screen that determine whether the answer is correct or not.
 *
 * @author Artem Tolpa, Seppo Hyvarinen, Lari Kettunen
 */

public class MiniGame implements Screen {

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


    public static float problemWidth = 900f;
    public static float problemHeight = 200f;

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
    int touchX = 0;
    int touchY = 0;
    int mIndex;

    boolean hide = false;
    int answerCounter = 0;
    Viewport viewport;
    Music thatsPlayed;

    /**
     * Constructor for the Minigame class. This constructor builds the ArrayLists for questions, determines which category of question is
     * presented to the user and builds the touch areas for the Android screen so player can answer the questions by tapping the screen.
     *
     * @param e    is a String value that determines which type of question will be presented to the player.
     * @param host is the Main object that handles the switching of screens between the Minigame and main game.
     */

    public MiniGame(String e, Main host) {
        batch = host.theGame.hudbatch;
        this.host = host;



        viewport = new FitViewport(TowerOfLife.WORLD_WIDTH, TowerOfLife.WORLD_HEIGHT, TowerOfLife.camera);
        viewport.apply();

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

        fearProblems.add(host.getLevelText("fearproblem1"));
        fearProblems.add(host.getLevelText("fearproblem2"));
        fearProblems.add(host.getLevelText("fearproblem3"));

        fearProblemOne.add(host.getLevelText("fear1_wrong1"));
        fearProblemOne.add(host.getLevelText("fear1_wrong2"));
        fearProblemOne.add(host.getLevelText("fear1_correct"));

        fearProblemTwo.add(host.getLevelText("fear2_wrong1"));
        fearProblemTwo.add(host.getLevelText("fear2_wrong2"));
        fearProblemTwo.add(host.getLevelText("fear2_correct"));

        fearProblemThree.add(host.getLevelText("fear3_wrong1"));
        fearProblemThree.add(host.getLevelText("fear3_wrong2"));
        fearProblemThree.add(host.getLevelText("fear3_correct"));

        sorrowProblems.add(host.getLevelText("sorrowproblem1"));
        sorrowProblems.add(host.getLevelText("sorrowproblem2"));
        sorrowProblems.add(host.getLevelText("sorrowproblem3"));

        sorrowProblemOne.add(host.getLevelText("sorrow1_wrong1"));
        sorrowProblemOne.add(host.getLevelText("sorrow1_wrong2"));
        sorrowProblemOne.add(host.getLevelText("sorrow1_correct"));

        sorrowProblemTwo.add(host.getLevelText("sorrow2_wrong1"));
        sorrowProblemTwo.add(host.getLevelText("sorrow2_wrong2"));
        sorrowProblemTwo.add(host.getLevelText("sorrow2_correct"));

        sorrowProblemThree.add(host.getLevelText("sorrow3_wrong1"));
        sorrowProblemThree.add(host.getLevelText("sorrow3_wrong2"));
        sorrowProblemThree.add(host.getLevelText("sorrow3_correct"));

        hateProblems.add(host.getLevelText("hateproblem1"));
        hateProblems.add(host.getLevelText("hateproblem2"));
        hateProblems.add(host.getLevelText("hateproblem3"));

        hateProblemOne.add(host.getLevelText("hate1_wrong1"));
        hateProblemOne.add(host.getLevelText("hate1_wrong2"));
        hateProblemOne.add(host.getLevelText("hate1_correct"));

        hateProblemTwo.add(host.getLevelText("hate2_wrong1"));
        hateProblemTwo.add(host.getLevelText("hate2_wrong2"));
        hateProblemTwo.add(host.getLevelText("hate2_correct"));

        hateProblemThree.add(host.getLevelText("hate3_wrong1"));
        hateProblemThree.add(host.getLevelText("hate3_wrong2"));
        hateProblemThree.add(host.getLevelText("hate3_correct"));

        pIndex = MathUtils.random(0, 2);
        mIndex = MathUtils.random(0, 2);

        if (e.equals("sorrowBox")) {

            if (mIndex == 0) {
                thatsPlayed = sorrowM;
            }  else if (mIndex == 1) {
                thatsPlayed = host.resources.sorrowM2;
            }  else {
                thatsPlayed = host.resources.sorrowM3;
            }

            if (host.theGame.musicOn) {
                thatsPlayed.play();
                thatsPlayed.setLooping(true);
            }

            problemList.addAll(sorrowProblems);
            if (TowerOfLife.usedSorrowQuestions.size() == sorrowProblems.size()) {
                TowerOfLife.usedSorrowQuestions.clear();
            }
            while (TowerOfLife.usedSorrowQuestions.contains(pIndex) || pIndex == TowerOfLife.latestSorrow) {
                pIndex = MathUtils.random(0, sorrowProblems.size() - 1);
            }
            TowerOfLife.usedSorrowQuestions.add(pIndex);
            TowerOfLife.latestSorrow = pIndex;
        } else if (e.equals("hateBox")) {

            if (host.theGame.anger) {
                if (mIndex == 0) {
                    thatsPlayed = angerM;
                }  else if (mIndex == 1) {
                    thatsPlayed = host.resources.angerM2;
                }  else {
                    thatsPlayed = host.resources.angerM3;
                }
                host.theGame.anger = false;
            }  else {
                if (mIndex == 0) {
                    thatsPlayed = hateM;
                }  else if (mIndex == 1) {
                    thatsPlayed = host.resources.hateM2;
                }  else {
                    thatsPlayed = host.resources.hateM3;
                }
            }


            if (host.theGame.musicOn) {
                thatsPlayed.play();
                thatsPlayed.setLooping(true);
            }
            problemList.addAll(hateProblems);
            if (TowerOfLife.usedAngerQuestions.size() == hateProblems.size()) {
                TowerOfLife.usedAngerQuestions.clear();
            }
            while (TowerOfLife.usedAngerQuestions.contains(pIndex) || TowerOfLife.latestAnger == pIndex) {
                pIndex = MathUtils.random(0, hateProblems.size() - 1);
            }
            TowerOfLife.usedAngerQuestions.add(pIndex);
            TowerOfLife.latestAnger = pIndex;
        } else {
            if (mIndex == 0) {
                thatsPlayed = fearM;
            }  else if (mIndex == 1) {
                thatsPlayed = host.resources.fearM2;
            }  else {
                thatsPlayed = host.resources.fearM3;
            }
            if (host.theGame.musicOn) {
                thatsPlayed.play();
                thatsPlayed.setLooping(true);
            }
            problemList.addAll(fearProblems);
            if (TowerOfLife.usedFearQuestions.size() == fearProblems.size()) {
                TowerOfLife.usedFearQuestions.clear();
            }
            while (TowerOfLife.usedFearQuestions.contains(pIndex) || TowerOfLife.latestFear == pIndex) {
                pIndex = MathUtils.random(0, fearProblems.size() - 1);
            }
            TowerOfLife.usedFearQuestions.add(pIndex);
            TowerOfLife.latestFear = pIndex;

        }

        problem = problemList.get(pIndex);

        if (e.equals("sorrowBox")) {
            if (pIndex == 0) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = sorrowProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 2400;
                    correctYLowerlimit = 1800;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = sorrowProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 1600;
                    correctYLowerlimit = 1000;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = sorrowProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 800;
                    correctYLowerlimit = 200;
                }
            } else if (pIndex == 1) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = sorrowProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 2400;
                    correctYLowerlimit = 1800;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = sorrowProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 1600;
                    correctYLowerlimit = 1000;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = sorrowProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 800;
                    correctYLowerlimit = 200;
                }
            } else {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = sorrowProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 2400;
                    correctYLowerlimit = 1800;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = sorrowProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 1600;
                    correctYLowerlimit = 1000;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = sorrowProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 800;
                    correctYLowerlimit = 200;
                }
            }
        } else if (e.equals("fearBox")) {
            if (pIndex == 0) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = fearProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 2400;
                    correctYLowerlimit = 1800;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = fearProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 1600;
                    correctYLowerlimit = 1000;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = fearProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 800;
                    correctYLowerlimit = 200;
                }

            } else if (pIndex == 1) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = fearProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 2400;
                    correctYLowerlimit = 1800;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = fearProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 1600;
                    correctYLowerlimit = 1000;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = fearProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 800;
                    correctYLowerlimit = 200;
                }
            } else {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = fearProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 2400;
                    correctYLowerlimit = 1800;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = fearProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 1600;
                    correctYLowerlimit = 1000;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = fearProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 800;
                    correctYLowerlimit = 200;
                }
            }
        } else {
            if (pIndex == 0) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = hateProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 2400;
                    correctYLowerlimit = 1800;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = hateProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 1600;
                    correctYLowerlimit = 1000;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = hateProblemOne.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 800;
                    correctYLowerlimit = 200;
                }

            } else if (pIndex == 1) {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = hateProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 2400;
                    correctYLowerlimit = 1800;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = hateProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 1600;
                    correctYLowerlimit = 1000;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = hateProblemTwo.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 800;
                    correctYLowerlimit = 200;
                }
            } else {
                aIndex = MathUtils.random(0, 2);
                int helper = aIndex;
                ans1 = hateProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 2400;
                    correctYLowerlimit = 1800;
                }
                while (helper == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                int helper2 = aIndex;
                ans2 = hateProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 1600;
                    correctYLowerlimit = 1000;
                }
                while (helper == aIndex || helper2 == aIndex) {
                    aIndex = MathUtils.random(0, 2);
                }
                ans3 = hateProblemThree.get(aIndex);
                if (aIndex == 2) {
                    correctXLowerlimit = 0;
                    correctXUpperlimit = 1800;
                    correctYUpperlimit = 800;
                    correctYLowerlimit = 200;
                }
            }
        }
    }

    /**
     * Mandatory method for classes implementing the screen. Renders all the Textures used in the minigame to the players screen.
     *
     * @param delta is the deltatime, or elapsed time.
     */

    public void render(float delta) {
        float ypos = 0;
        batch.begin();
        if (!hide) {
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.draw(minigameBg, 0, 0, 1800, 3200);


            batch.draw(problemBox, 0, TowerOfLife.WORLD_HEIGHT * 200 - 600, problemWidth * 2, 600);
            ypos = TowerOfLife.WORLD_HEIGHT * 200 - 300f + getTextHeight(problem);
            host.resources.minigameFont.draw(batch, problem, 100, ypos, 1600f, 1, true);

            if (itsCorrect && touchY > 1800 && touchY < 2400) {
                batch.draw(host.resources.answerBoxRight, 0, 1800, 1800, 600);
            }  else if(touchY > 1800 && touchY < 2400) {
                batch.draw(host.resources.answerBoxWrong, 0, 1800, 1800, 600);
            }  else {
                batch.draw(answerBox, 0, 1800, 1800, 600);
            }

            ypos = 2100 + getTextHeight(ans1);
            host.resources.minigameFont.draw(batch, ans1, 220, ypos, 1360f, 1, true);

            if (itsCorrect && touchY > 1000 && touchY < 1600) {
                batch.draw(host.resources.answerBoxRight, 0, 1000, 1800, 600);
            }  else if(touchY > 1000 && touchY < 1600) {
                batch.draw(host.resources.answerBoxWrong, 0, 1000, 1800, 600);
            }  else {
                batch.draw(answerBox, 0, 1000, 1800, 600);
            }

            ypos = 1300 + getTextHeight(ans2);
            host.resources.minigameFont.draw(batch, ans2, 220, ypos, 1360f, 1, true);


            if (itsCorrect && touchY > 200 && touchY < 800) {
                batch.draw(host.resources.answerBoxRight, 0, 200, 1800, 600);
            }  else if (touchY > 200 && touchY < 800)  {
                batch.draw(host.resources.answerBoxWrong, 0, 200, 1800, 600);
            }  else {
                batch.draw(answerBox, 0, 200, 1800, 600);
            }
            ypos = 500 + getTextHeight(ans3);
            host.resources.minigameFont.draw(batch, ans3, 220, ypos, 1360f, 1, true);
            if (answerIsGiven) {
                answerCounter++;

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

    /**
     * Method that creates a Vector3 touchpoint from the point the user touches at the screen. This touchpoint is then relayed to another method for checking
     * whether the answer is correct (is the touchpoint within the determined "correct" area).
     */

    public void choose() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPoint = new Vector3();
            TowerOfLife.hudcamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            touchY = (int) touchPoint.y;

            //answer 1
            if ((touchPoint.x > 0 && touchPoint.x < 1800) && (touchPoint.y > 1800 && touchPoint.y < 2400)) {
                isAnswerRight((int) touchPoint.x, (int) touchPoint.y);
                answerIsGiven = true;
            } else if ((touchPoint.x > 0 && touchPoint.x < 1800) && (touchPoint.y > 200 && touchPoint.y < 800)) {
                isAnswerRight((int) touchPoint.x, (int) touchPoint.y);
                answerIsGiven = true;
            }/*answer2*/ else if ((touchPoint.x > 0 && touchPoint.x < 1800) && (touchPoint.y > 1000 && touchPoint.y < 1600)) {
                isAnswerRight((int) touchPoint.x, (int) touchPoint.y);
                answerIsGiven = true;
            }
        }
    }

    /**
     * Method that determines whether the players answer is correct by using the Vector3 coordinates that are built in choose() method.
     *
     * @param x is the x coordinate of Vector3 that's created when user touches the screen.
     * @param y is the y coordinate of Vector3 that's created when user touches the screen.
     */

    public void isAnswerRight(int x, int y) {
        if ((x < correctXUpperlimit && x > correctXLowerlimit) && (y < correctYUpperlimit && y > correctYLowerlimit)) {
            if (host.theGame.musicOn) {
                thatsPlayed.stop();
            }
            if (!soundIsPlayed) {
                if (TowerOfLife.soundOn) {
                    correct.play();
                }
                itsCorrect = true;
                host.theGame.score += 10;
                host.theGame.bounceMultiplier = 0;
                host.theGame.scoreSoundPlayed = false;
                host.theGame.gongrats = true;
                soundIsPlayed = true;
                if (host.theGame.musicOn) {
                    host.theGame.resumeMusic = true;
                }
            }

        } else {
            if (host.theGame.musicOn) {
                thatsPlayed.stop();
            }
            if (!soundIsPlayed) {
                if (TowerOfLife.soundOn) {
                    incorrect.play();
                }
                host.theGame.bounceMultiplier *= 1 / 2f;
                host.theGame.scoreSoundPlayed = false;
                host.theGame.wasIncorrect = true;
                soundIsPlayed = true;
                if (host.theGame.musicOn) {
                    host.theGame.resumeMusic = true;
                }

            }
        }
    }

    /**
     * Method for centering the texts used in the questions to the middle of answerboxes.
     * <p>
     * It takes String as a parameter and determines how many lines are needed for the said text.
     *
     * @param text is the text that is needed to be centered.
     */

    public float getTextHeight(String text) {
        int textLength = text.length();
        GlyphLayout layout = new GlyphLayout();
        layout.setText(host.resources.minigameFont, text);
        float fontHeight = layout.height;
        float halfHeight = 0;
        if (textLength < 31) { // 1 row
            halfHeight = fontHeight * 0.5f + 0.1f;
        } else if (textLength < 62) { // 2 rows
            halfHeight = (fontHeight * 2 + 0.4f) / 2;
        } else if (textLength < 93) { // 3 rows
            halfHeight = (fontHeight * 3 + 0.8f) / 2;
        } else if (textLength < 124) { // 4 rows
            halfHeight = (fontHeight * 4 + 1.2f) / 2;
        } else if (textLength < 155) { // 5 rows
            halfHeight = (fontHeight * 5 + 1.6f) / 2;
        } else { // 6 rows or more. shouldn't ever happen!!
            halfHeight = (fontHeight * 6 + 2.0f) / 2;
        }
        return halfHeight;
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void show() {
    }

    /**
     * Mandatory method in classes that implement Screen. Handles stage scaling for different devices.
     */

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void pause() {
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void resume() {
    }

    /**
     * Mandatory method in classes that implement Screen. Doesn't do anything here.
     */

    @Override
    public void hide() {
    }

    /**
     * Mandatory method in classes that implement Screen. Disposes of Textures and Sounds used in the class.
     */

    @Override
    public void dispose() {
        correct.dispose();
        incorrect.dispose();
        minigameBg.dispose();
        problemBox.dispose();
        answerBox.dispose();
        host.resources.minigameFont.dispose();
        batch.dispose();
        thatsPlayed.dispose();
        hateM.dispose();
        fearM.dispose();
        sorrowM.dispose();
        angerM.dispose();
        nice.dispose();
        host.resources.minigameFont.dispose();
        host.resources.minigameFontGenerator.dispose();
    }
}