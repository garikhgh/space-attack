package org.armos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import static org.armos.Constants.HEIGHT;
import static org.armos.Constants.WIDTH;

public class MainMenuScreen implements Screen {

    private static  final int EXIT_BUTTON_WIDTH = 300;
    private static  final int EXIT_BUTTON_HEIGHT = 150;

    private static  final int PLAY_BUTTON_WIDTH = 300;
    private static  final int PLAY_BUTTON_HEIGHT = 150;

    private static  final int PLAY_Y = 550;
    private static  final int EXIT_Y = 350;



    Texture exitButtonActive;
    Texture exitButtonInActive;
    Texture playButtonActive;
    Texture playButtonInActive;

    SpaceAttack spaceAttack;
    public MainMenuScreen(SpaceAttack spaceAttack) {
        this.spaceAttack = spaceAttack;
        playButtonActive = new Texture("play_button_active.png");
        playButtonInActive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInActive = new Texture("exit_button_inactive.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        this.spaceAttack.batch.begin();

        float x = (float) WIDTH / 2 - (float) EXIT_BUTTON_WIDTH / 2;
        if (Gdx.input.getX() <= x + EXIT_BUTTON_WIDTH && Gdx.input.getX() >= x &&
                HEIGHT - Gdx.input.getY() <= EXIT_Y + EXIT_BUTTON_HEIGHT && HEIGHT - Gdx.input.getY() >= EXIT_Y) {
            this.spaceAttack.batch.draw(exitButtonActive, x , EXIT_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            this.spaceAttack.batch.draw(exitButtonInActive, x, EXIT_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        if (Gdx.input.getX() <= x + PLAY_BUTTON_WIDTH && Gdx.input.getX() >= x &&
                HEIGHT - Gdx.input.getY() <= PLAY_Y + PLAY_BUTTON_HEIGHT && HEIGHT - Gdx.input.getY() >= PLAY_Y) {
            this.spaceAttack.batch.draw(playButtonActive, x , PLAY_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.spaceAttack.setScreen(new MainGameScreen(spaceAttack));
            }
        } else {
            this.spaceAttack.batch.draw(playButtonInActive, x, PLAY_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
        this.spaceAttack.batch.end();


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
