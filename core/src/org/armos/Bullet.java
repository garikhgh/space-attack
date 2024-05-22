package org.armos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.armos.tools.CollisionRect;

public class Bullet {
    public static final int SPEED_BULLET = 80;
    public static final int BULLET_WIDTH = 3;
    public static final int BULLET_HEIGHT = 12;


    public static final int DEFAULT_Y = 40;
    private static Texture texture;
    float x, y;
    private final CollisionRect collisionRect;
    public boolean remove = false;

    public Bullet(float x) {
        this.x = x;
        this.y = DEFAULT_Y;
        this.collisionRect = new CollisionRect(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        if (texture == null) {
            texture = new Texture("bullet.png");
        }
    }
    public void update(float deltaTime) {
        y += SPEED_BULLET + deltaTime;
        if (y > Gdx.graphics.getHeight()) {
            remove = true;
        }
        this.collisionRect.move(x, y);
    }
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
    public CollisionRect getCollisionRect() {
        return this.collisionRect;
    }

}
