package com.catani.vgravity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


//** ScrollingBackground Class to be used in ScrGame Class **\\
public class ScrollingBackground {

    Texture[] txtBackgrounds = new Texture[3];

    private float fX = 0, fX2 = 1920, fY = 0;
    private int nWidth = Constants.WORLDWIDTH, nHeight = Constants.WORLDHEIGHT;
    float fScrollSpeed;
    private int nImage = 0, nImage2 = 0;
    private int nScore = 0;
    private int nChooseImage;

    public ScrollingBackground() {
        txtBackgrounds[0] = new Texture("background1.png");
        txtBackgrounds[1] = new Texture("backgroundimage2.jpg");
        txtBackgrounds[2] = new Texture("backgroundimage3.jpg");
    }

    public void render(SpriteBatch batch) {

        batch.draw(txtBackgrounds[nImage], fX, fY, nWidth, nHeight);
        batch.draw(txtBackgrounds[nImage], fX2, fY, nWidth, nHeight);
        scroll();
        // Change();
    }

    private void scroll() {
        //System.out.println("X1 = "+ fX + " X2 = " + fX2);
        fX -= fScrollSpeed;
        fX2 -= fScrollSpeed;
        if (fX <= nWidth * -1) { //if off screen to the left
            fX = fX2 + nWidth; //move to off screen to the right
        }
        if (fX2 <= nWidth * -1) {
            fX2 = fX + nWidth;
        }
    }

    private void Change() { //** NEEDS MAJOR EDITING WHEN WE START USING THIS **
        nScore++;
        if (nScore % 500 == 0) {
            nChooseImage = MathUtils.random(0, 2);
        }
        if (fX == nWidth) { //change image when image is off screen
            nImage = nChooseImage;
        }
        if (fX2 == nWidth) {
            nImage2 = nChooseImage;
        }
    }
    public float getScrollSpeed(){
        return fScrollSpeed;
    }
    public void setSrcollSpeed(float scollSpeed){
       fScrollSpeed = scollSpeed;
    }
}

