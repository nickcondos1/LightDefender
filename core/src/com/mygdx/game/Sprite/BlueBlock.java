package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class BlueBlock
{
    private Texture elephant;
    private Vector3 position;
    private Rectangle bounds;
    private Random rand;
    private int xVal;
    public int pos = 10;
    public int neg = -10;


    public BlueBlock(int x, float y)
    {
        elephant = new Texture("blueblock1.png");
        elephant.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rand = new Random();
        position = new Vector3(x,y,0); //2000
        bounds = new Rectangle(position.x, position.y, elephant.getWidth(), elephant.getHeight());
        xVal = x;

    }

    public void update(float dt)
    {
        if (dt > 0)
        {
            if (xVal < 0)
                position.add(pos,0,0);
            if (xVal > 0)
                position.add(neg,0,0);

            bounds.setPosition(position.x, position.y);
        }
    }

    public Texture getBlueBlock() {
        return elephant;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void dispose()
    {
        elephant.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(Vector3 position)
    {
        this.position = position;
    }

    public void reposition()
    {
        position.set(xVal, (rand.nextInt(2500 - 1750 ) + 1750) + position.y + 500, 0);
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }

}
