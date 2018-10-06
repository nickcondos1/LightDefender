package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.LightDefender;

import java.util.Random;

public class Light
{
    private Texture light;
    private Vector3 position;
    private Rectangle bounds;
    private Random rand;

    public Light(float y)
    {
        light = new Texture("light1.png");
        light.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rand = new Random();
        position = new Vector3(rand.nextInt(LightDefender.WIDTH - light.getWidth()), y,0);
        bounds = new Rectangle(position.x, position.y, light.getWidth(), light.getHeight());
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

    public Texture getLight() {
        return light;
    }

    public void dispose()
    {
        light.dispose();
    }

    public void reposition()
    {
        position.set(rand.nextInt(LightDefender.WIDTH - light.getWidth()), 4000 + position.y, 0);
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(bounds);
    }
}
