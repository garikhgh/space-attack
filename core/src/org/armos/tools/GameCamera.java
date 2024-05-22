package org.armos.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static org.armos.constants.Constants.HEIGHT;
import static org.armos.constants.Constants.WIDTH;

public class GameCamera {
    private OrthographicCamera cam;
    private StretchViewport viewport;

    public GameCamera(int width, int height) {
        this.cam = new OrthographicCamera();
        this.viewport = new StretchViewport(width, height, this.cam);
        this.viewport.apply();
        this.cam.position.set((float) width / 2, (float) height / 2, 0);
        cam.update();
    }

    public Matrix4 combined() {
        return this.cam.combined;
    }
    public void update(int width, int height) {
        viewport.update(width, height);
    }
    public Vector2 getInputGameWorld() {
        Vector3 inputScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 unproject = cam.unproject(inputScreen);
        return new Vector2(unproject.x, unproject.y);

    }
}
