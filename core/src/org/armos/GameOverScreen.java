package org.armos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import static org.armos.constants.Constants.HEIGHT;
import static org.armos.constants.Constants.WIDTH;

public class GameOverScreen implements Screen {
    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;

    Texture gameOverBanner;
    BitmapFont scoreFont;
    SpaceAttack game;
    int score, highScore;

    public GameOverScreen(SpaceAttack game, int score) {
        this.game = game;
        this.score = score;
        // get high score from saved file
        Preferences prefs = Gdx.app.getPreferences("spacegame");
        this.highScore = prefs.getInteger("highscore", 0);
        //check if score beats highscore;
        if (score > highScore) {
            prefs.putInteger("highscore", score);
            prefs.flush();
        }
        // Load texture and fonts
        gameOverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        this.game.batch.begin();
        this.game.scrollingBackground.updateAndRender(delta, this.game.batch);

        this.game.batch.draw(gameOverBanner, (float) Gdx.graphics.getWidth() / 2 - (float) BANNER_WIDTH / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - 15, BANNER_WIDTH, BANNER_HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE, 0, Align.left, false);
        GlyphLayout highScoreLayout = new GlyphLayout(scoreFont, "High Score: \n" + score, Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.batch, scoreLayout, (float) Gdx.graphics.getWidth() /2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - 15 * 2);
        scoreFont.draw(game.batch, highScoreLayout, (float) Gdx.graphics.getWidth() /2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - scoreLayout.height - 15 * 3);


        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");
        float tryAgainX = (float) Gdx.graphics.getWidth() / 2 - tryAgainLayout.width /2;
        float tryAgainY = (float) Gdx.graphics.getHeight() / 2 - tryAgainLayout.height /2;

        float mainMenuX = (float) Gdx.graphics.getWidth() / 2 - tryAgainLayout.width /2;
        float mainMenuY = (float) Gdx.graphics.getHeight() / 2 - tryAgainLayout.height /2 - tryAgainLayout.height - 15;


        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        //if tgrAgain is pressed
        if (Gdx.input.isTouched()) {
            // try again
            if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                 this.dispose();
                 this.game.batch.end();
                 this.game.setScreen(new MainGameScreen(this.game));
                 return;
            }

            if (touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
                this.dispose();
                this.game.batch.end();
                this.game.setScreen(new MainMenuScreen(this.game));
                return;
            }
        }

        //Draw buttons
        scoreFont.draw(this.game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(this.game.batch, mainMenuLayout, mainMenuX, mainMenuY);



        this.game.batch.end();
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

    }
}
