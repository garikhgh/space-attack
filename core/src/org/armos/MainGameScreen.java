package org.armos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import org.armos.constants.Constants;
import org.armos.entities.Asteroid;
import org.armos.entities.Bullet;
import org.armos.entities.Explosion;
import org.armos.tools.CollisionRect;
import org.armos.tools.GameCamera;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.armos.constants.Constants.HEIGHT;
import static org.armos.constants.Constants.WIDTH;

public class MainGameScreen implements Screen {

    public static final float SPEED = 20;
    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;
    public static final float ROLL_TIMER_SWITCH_TIME = 0.15f;
    public static final float SHOOT_WAIT_TIME = 0.3f;
    public static final float MIN_ASTEROID_SPAWN_TIME = 0.03f;
    public static final float MAX_ASTEROID_SPAWN_TIME = 0.1f;


    Animation[] rolls;
    List<Bullet> bulletList;
    List<Asteroid> asteroidList;
    List<Explosion> explosionList;
    Texture blank;

    private float rollTimer;
    private float y;
    private float x;
    private float stateTime;
    private float shootTimer;
    private float asteroidSpawnTimer;
    int roll;
    int score = 0;
    Random random;


    Texture controls;
    SpaceAttack game;
    BitmapFont scoreFont;
    CollisionRect playerRect;
    float health = 1; // 0 means dead 1 means health

    boolean showControls = true;

    public MainGameScreen(SpaceAttack spaceAttack) {
        this.scoreFont = new BitmapFont(Gdx.files.internal("font/score.fnt"));
        this.game = spaceAttack;
        y = 15;
        x = (float) WIDTH / 2 - (float) SHIP_WIDTH / 2;
        bulletList = new ArrayList<>();
        asteroidList = new ArrayList<>();
        explosionList = new ArrayList<>();
        roll = 2;
        rollTimer = 0;
        shootTimer = 0;
        random = new Random();
        asteroidSpawnTimer = random.nextFloat() *  (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
        rolls = new Animation[5];
        blank = new Texture("blank.png");
        playerRect = new CollisionRect(0, 0, SHIP_WIDTH, SHIP_HEIGHT);
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);
        rolls[0] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[2]);
        rolls[1] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[1]);
        rolls[2] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);
        rolls[3] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[3]);
        rolls[4] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[4]);
        this.game.scrollingBackground.setSpeedFixed(true);

        if (SpaceAttack.IS_MOBILE) {
            controls = new Texture("controls.png");
        }
    }

    @Override
    public void show() {
//        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {

        shootTimer += delta;
        // Shooting
        if ((isLeft() || isRight()) || Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer=0;
            // stop showing controls
            showControls = false;
            int offset= 4;

            if (roll == 1 || roll ==3)
                offset = 8;
            if (roll == 0 || roll == 4)
                offset = 16;

            bulletList.add(new Bullet(x + offset));
            bulletList.add(new Bullet(x + SHIP_WIDTH-offset));

        }
        //Asteroid spawn code
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer  <= 0) {
            asteroidSpawnTimer = random.nextFloat() *  (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            asteroidList.add(new Asteroid(random.nextInt(WIDTH-Asteroid.ASTEROID_WIDTH)));
        }

        List<Asteroid> asteroidToRemove = new ArrayList<>();
        for (Asteroid asteroid: asteroidList) {
            asteroid.update(delta);
            if (asteroid.remove) {
                asteroidToRemove.add(asteroid);
            }
        }

        // UpdateExplosion
        List<Explosion> explosionsToRemove = new ArrayList<>();
        for (Explosion explosion : explosionList) {
            explosion.update(delta);
            if (explosion.remove) {
                explosionsToRemove.add(explosion);
            }
        }
        explosionList.removeAll(explosionsToRemove);


        //Update Bullet
        List<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : bulletList) {
            bullet.update(delta);
            if (bullet.remove) {
                bulletsToRemove.add(bullet);
            }
        }
        // After player moves, update collision rect;
        playerRect.move(x, y);
        //After all updates, check for collisions
        for (Bullet bullet : bulletList) {
            for (Asteroid asteroid : asteroidList) {
                if (bullet.getCollisionRect().collidesWith(asteroid.getCollisionRect())) {
                    // collision occured;
                    bulletsToRemove.add(bullet);
                    asteroidToRemove.add(asteroid);
                    explosionList.add(new Explosion(asteroid.getX(), asteroid.getY()));
                    score +=100;
                }
            }
        }

        for (Asteroid asteroid : asteroidList) {
            if (asteroid.getCollisionRect().collidesWith(playerRect)) {
                asteroidToRemove.add(asteroid);
                health -= 0.1f;
                if (health <= 0 ) {
                    this.dispose();
                    this.game.setScreen(new GameOverScreen(this.game, score));
                    return;
                }
            }
        }

        asteroidList.removeAll(asteroidToRemove);
        bulletList.removeAll(bulletsToRemove);


        //Movement
        stateTime += delta;
        if (isLeft()) {
            x -= SPEED + Gdx.graphics.getDeltaTime();
            if (x < 0){
                x = 0;
            }

            // update roll if button just clicked;
            if (isJustLeft() && !isJustRight() && roll > 0) {
                rollTimer = 0;
                roll --;
            }
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                rollTimer -= ROLL_TIMER_SWITCH_TIME;
                roll--;
            }
        } else {
            if (roll < 2) {
                // update roll to make it go back to center
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4);{
                    rollTimer -= ROLL_TIMER_SWITCH_TIME;
                    roll++;
                }
            }
        }
        if (isRight()) {
            x += SPEED + Gdx.graphics.getDeltaTime();
            if (x + SHIP_WIDTH > Gdx.graphics.getWidth()) {
                x = Gdx.graphics.getWidth() - SHIP_WIDTH;
            }

            if (isJustRight()&& !isJustLeft() && roll > 0) {
                rollTimer = 0;
                roll --;
            }

            rollTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4) {
                rollTimer -= ROLL_TIMER_SWITCH_TIME;
                roll++;
            }
        } else {
            if (roll > 2){
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0){
                    rollTimer -= ROLL_TIMER_SWITCH_TIME;
                    roll--;
                }
            }
        }

        ScreenUtils.clear(0, 0, 0, 1);
        this.game.batch.begin();
        this.game.scrollingBackground.updateAndRender(delta, game.batch);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
        scoreFont.draw(this.game.batch, scoreLayout, (float) WIDTH / 2 - scoreLayout.width, HEIGHT - scoreLayout.height - 10);

        TextureRegion keyFrame = (TextureRegion) rolls[roll].getKeyFrame(stateTime, true);
        for (Bullet bullet : bulletList) {
            bullet.render(this.game.batch);
        }
        for (Asteroid asteroid: asteroidList){
            asteroid.render(this.game.batch);
        }
        for (Explosion explosion : explosionList) {
            explosion.render(this.game.batch);
        }

        // draw helth
        if (health > 0.6f ) {
            this.game.batch.setColor(Color.GREEN);
        } else if (health > 0.2f) {
            this.game.batch.setColor(Color.ORANGE);
        } else {
            this.game.batch.setColor(Color.RED);
        }
        this.game.batch.draw(blank, 0, 0, WIDTH * health, 5);
        this.game.batch.setColor(Color.WHITE);
        this.game.batch.draw(keyFrame, x, y, SHIP_WIDTH, SHIP_HEIGHT);

        // draw controls constructions
        if (showControls){
            if (SpaceAttack.IS_MOBILE) {
                //draw left
                game.batch.setColor(Color.RED);
                game.batch.draw(controls, 0, 0, (float) WIDTH / 2, (float) HEIGHT /2, 0, 0, WIDTH, HEIGHT, false, false);

                //draw right
                game.batch.setColor(Color.BLUE);
                game.batch.draw(controls, (float) WIDTH / 2, 0, (float) WIDTH / 2, (float) HEIGHT / 2, 0, 0, WIDTH, HEIGHT, true, false);

                game.batch.setColor(Color.WHITE);
            } else {
                GlyphLayout instructionsLayout = new GlyphLayout(scoreFont, "Left/Right" +"\n"+ "to Shoot", Color.WHITE, WIDTH - 50, Align.left, true);
                scoreFont.draw(game.batch, instructionsLayout, WIDTH/2- instructionsLayout.width, 150);
            }

        }

        this.game.batch.end();

    }

    private boolean isRight() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input.isTouched() && Gdx.input.getX() >= WIDTH / 2);
    }

    private boolean isLeft() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isTouched() && Gdx.input.getX() < WIDTH / 2);

    }

    private boolean isJustRight() {
        return Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.justTouched() && Gdx.input.getX() >= WIDTH / 2);

    }

    private boolean isJustLeft() {
        return Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.justTouched() && Gdx.input.getX() < WIDTH / 2);

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
