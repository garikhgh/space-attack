package org.armos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.armos.tools.CollisionRect;
import org.armos.utils.TextureUtils;

import java.util.Random;

public class Asteroid {
    public static final int SPEED_ASTEROID = 5;

    public static final int ASTEROID_WIDTH = 48;
    public static final int ASTEROID_HEIGHT = 48;
    private static Texture texture;
    float x, y;
    public boolean remove = false;
    private Random random = new Random();
    private final CollisionRect collisionRect;

    public Asteroid(float x) {
        this.x = x;
        this.y = Gdx.graphics.getHeight();
        if (texture == null) {
            texture = TextureUtils.readTexture("asteroid.png", ASTEROID_WIDTH, ASTEROID_HEIGHT);
        }
        this.collisionRect = new CollisionRect(x, y, ASTEROID_WIDTH, ASTEROID_HEIGHT);
    }
    public void update(float deltaTime) {
        y -= SPEED_ASTEROID + deltaTime;
        if (y < -ASTEROID_HEIGHT) {
            remove = true;
        }
        collisionRect.move(x, y);
    }
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
    public CollisionRect getCollisionRect() {
        return this.collisionRect;
    }

}
