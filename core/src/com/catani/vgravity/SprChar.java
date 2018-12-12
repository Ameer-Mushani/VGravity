package com.catani.vgravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by am44_000 on 2018-09-12.
 */


public class SprChar extends Sprite {
    private Vector2 vVel;
    private Texture SpriteSheet, SpriteSheetInverted;
    private TextureRegion[][] tmp, tmp2;
    private TextureRegion[] txtregRunLeft, txtRegRunRight, txtRegRunRightInv;
    private Animation[] AnmCreateAnimation;
    private int index = 0;
    private float ElapsedTime = 0;
    private boolean isGoingUp = false;

    public SprChar() {
//        super(new Texture("right.png"));
        SpriteSheet = new Texture("SprPlayer.png");
        SpriteSheetInverted = new Texture("SprPlayerInverted.png");
        PrepareAnimations(2, 4);
        vVel = new Vector2(0, -20);
        this.setSize(92, 150);
    }

    public void render(SpriteBatch batch) {
        ElapsedTime += Gdx.graphics.getDeltaTime();

        setX(getX() + vVel.x);
        setY(getY() + vVel.y);

        if (vVel.y == -20) {
            isGoingUp = false;
        }
        if (vVel.y == 20) {
            isGoingUp = true;
        }


        if (!isGoingUp) {
            batch.draw((TextureRegion) AnmCreateAnimation[0].getKeyFrame(ElapsedTime, true), getX(), getY()); //normal
        }
        if (isGoingUp) {
            batch.draw((TextureRegion) AnmCreateAnimation[2].getKeyFrame(ElapsedTime, true), getX(), getY()); //inverted
        }

    }

    public void reset() {
        setPosition(500, Constants.FLOOR );
        vVel.set(0, 20);
    }

    public void PrepareAnimations(int rows, int columns) {
        tmp = TextureRegion.split(SpriteSheet, SpriteSheet.getWidth() / columns, SpriteSheet.getHeight() / rows);
        tmp2 = TextureRegion.split(SpriteSheetInverted, SpriteSheetInverted.getWidth() / columns, SpriteSheetInverted.getHeight() / rows);

        txtregRunLeft = new TextureRegion[4];
        txtRegRunRight = new TextureRegion[4];
        txtRegRunRightInv = new TextureRegion[4];

        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < columns; j++) {
                txtregRunLeft[index++] = tmp[i][j];
            }
        }

        index = 0;

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                txtRegRunRight[index++] = tmp[i][j];
            }
        }

        index = 0;

        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < columns; j++) {
                txtRegRunRightInv[index++] = tmp2[i][j];
            }
        }

        AnmCreateAnimation = new Animation[3];

        AnmCreateAnimation[0] = new Animation<TextureRegion>(0.10f, txtregRunLeft); // right
        AnmCreateAnimation[1] = new Animation<TextureRegion>(0.10f, txtRegRunRight); // left
        AnmCreateAnimation[2] = new Animation<TextureRegion>(0.10f, txtRegRunRightInv); // right inverted
    }

    public void constrain() {
        if (getY() <= Constants.FLOOR || getY() >= Constants.CEILING - getHeight()) {
            setYVel(0);
        }
    }


    public void flipY() {
        vVel.y *= -1;
    }

    public void setXVel(float xVel) {
        vVel.x = xVel;
    }

    public void setYVel(float yVel) {
        vVel.y = yVel;
    }

    public float getXVel() {
        return vVel.x;
    }

    public float getYVel() {
        return vVel.y;
    }
}