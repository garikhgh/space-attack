package org.armos.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static org.armos.constants.Constants.WIDTH;

public class ScrollingBackground {

    public static final int DEFAULT_SPEED = 200;
    public static final int ACCELERATION = 50;
    public static final int GOAL_REACH_ACCELERATION = 200;



    Texture image;
    float y1, y2;
    float imageScale;
    boolean speedFixed = true;

    int speed; // in pixels / second
    int goalSpeed;

    public ScrollingBackground() {
        image = new Texture("stars_background.png");
        y1 = 0;
        y2 = image.getHeight();
        goalSpeed = DEFAULT_SPEED;
        speed = 0;
        imageScale = 0;
    }

    public void updateAndRender(float deltaTime, SpriteBatch batch) {
        // Speed adjustment to reach goal
        if (speed < goalSpeed) {
            speed += (int) (GOAL_REACH_ACCELERATION * deltaTime);
            if (speed > goalSpeed) {
                speed = goalSpeed;
            }
        } else if (speed > goalSpeed) {
            speed -= (int) (GOAL_REACH_ACCELERATION * deltaTime);
            if (speed < goalSpeed) {
                speed = goalSpeed;
            }
        }
        if (!speedFixed) {
            speed += (int) (ACCELERATION * deltaTime);
        }
        y1 -= speed * deltaTime;
        y2 -= speed * deltaTime;
        //if image reaches the bottom of screen is not visible put it back on top
        if (y1 + image.getHeight() * imageScale <= 0) {
            y1 = y2 + image.getHeight() * imageScale;
        }
        if (y1 + image.getHeight() * imageScale <= 0) {
            y2 = y1 + image.getHeight() * imageScale;
        }
        batch.draw(image, 0, y1, WIDTH, image.getHeight() * imageScale);
        batch.draw(image, 0, y2, WIDTH, image.getHeight() * imageScale);

    }

    public void resize(int width, int height) {
        imageScale = (float) width / image.getWidth();
    }

    public void setSpeed(int goalSpeed) {
        this.goalSpeed = goalSpeed;
    }
    public void setSpeedFixed(boolean speedFixed) {
        this.speedFixed = speedFixed;
    }
}
