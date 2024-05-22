package org.armos.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureUtils {
    private TextureUtils(){}
    public static Texture readTexture(String filePath, int width, int height) {
        Pixmap pixmap200 = new Pixmap(Gdx.files.internal(filePath));
        Pixmap pixmap100 = new Pixmap(width, height, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        return new Texture(pixmap100);
    }
}
