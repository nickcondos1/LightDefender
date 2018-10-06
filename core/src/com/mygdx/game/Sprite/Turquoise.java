package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.LightDefender;

import java.util.Random;

public class Turquoise
{
    private Texture turquoise;
    private Vector3 position;
    private Rectangle bounds;
    private Random rand;
    private float yVal;

    public Turquoise(float y)
    {
        turquoise = new Texture("redblock.png");
        turquoise.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rand = new Random();
        position = new Vector3(rand.nextInt(LightDefender.WIDTH - turquoise.getWidth()),y,0);
        bounds = new Rectangle(position.x, position.y, turquoise.getWidth(), turquoise.getHeight());
        yVal = y;

    }

    public void update(float dt)
    {
        if (dt > 0)
        {
            if (yVal > 0)
            {
                if (position.x > (LightDefender.WIDTH / 3 + LightDefender.WIDTH / 3))
                    position.add(0, -12, 0);
                else if (position.x < LightDefender.WIDTH / 3)
                    position.add(0, -8,0);
                else
                    position.add(0, -4, 0);
            }

            bounds.setPosition(position.x, position.y);
        }
    }

    public Texture getTurquoise() {
        return turquoise;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(int x)
    {
        position.add(x, 0, 0);
    }

    public void reposition(float y)
    {
        position.set(rand.nextInt(1290 - 0) + 0, y, 0);
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }

    public void dispose()
    {
        turquoise.dispose();
    }
}
