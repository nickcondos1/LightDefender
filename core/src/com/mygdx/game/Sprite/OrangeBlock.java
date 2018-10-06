package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class OrangeBlock
{
    private Texture armadillo;
    private Vector3 position;
    private Rectangle bounds;
    private Random rand;
    private int xVal;
    //private int yVal;

    public OrangeBlock(int x, float y)
    {
        armadillo = new Texture("orangeblock.png");
        armadillo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rand = new Random();
        position = new Vector3(x,y,0);
        bounds = new Rectangle(position.x, position.y, armadillo.getWidth(), armadillo.getHeight());
        xVal = x;
        //yVal = y;

    }

    public void update(float dt)
    {
        if (dt > 0)
        {
            position.add(0,0,0);
            bounds.setPosition(position.x, position.y);
        }
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getOrangeBlock() {
        return armadillo;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose()
    {
        armadillo.dispose();
    }

    public void reposition()
    {
        position.set(xVal, 4000 + position.y,0);
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }
}
