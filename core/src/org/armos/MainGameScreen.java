package org.armos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import static org.armos.Constants.SHIP_WIDTH;
import static org.armos.Constants.WIDTH;

public class MainGameScreen implements Screen {

    public static final float SPEED = 20;
    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;
    public static final float ROLL_TIMER_SWITCH_TIME = 0.15f;

    Animation[] rolls;

    private float rollTimer;
    private float y;
    private float x;
    private float stateTime;
    int roll;



//    Texture img;
    SpaceAttack game;

    public MainGameScreen(SpaceAttack spaceAttack) {
        this.game = spaceAttack;
        y = 15;
        x = (float) WIDTH / 2 - (float) SHIP_WIDTH / 2;
        roll = 2;
        rollTimer = 0;
        rolls = new Animation[5];
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);
        rolls[0] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[2]);
        rolls[1] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[1]);
        rolls[2] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);
        rolls[3] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[3]);
        rolls[4] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[4]);
    }

    @Override
    public void show() {
//        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {

        stateTime += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= SPEED + Gdx.graphics.getDeltaTime();
            if (x < 0){
                x = 0;
            }

            // update roll if button just clicked;
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && roll > 0) {
                rollTimer = 0;
                roll --;
            }
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                rollTimer = 0;
                roll--;
            }
        } else {
            if (roll < 2) {
                // update roll to make it go back to center
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4);{
                    rollTimer = 0;
                    roll++;
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += SPEED + Gdx.graphics.getDeltaTime();
            if (x + SHIP_WIDTH > Gdx.graphics.getWidth()) {
                x = Gdx.graphics.getWidth() - SHIP_WIDTH;
            }
            rollTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                rollTimer = 0;
                roll++;
            }
        } else {
            if (roll > 2){
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0){
                    rollTimer = 0;
                    roll--;
                }
            }
        }

        ScreenUtils.clear(0, 0, 0, 1);
        this.game.batch.begin();
        TextureRegion keyFrame = (TextureRegion) rolls[roll].getKeyFrame(stateTime, true);
        this.game.batch.draw(keyFrame, x, y, SHIP_WIDTH, SHIP_HEIGHT);
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
