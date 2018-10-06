package com.mygdx.game.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Character
{
    private Texture car;
    private Texture carMagenta;
    private Texture carPurple;
    private Vector3 position;
    private Rectangle bounds;
    private int carNum;
    private Preferences prefs;

    public Character(int x, int y)
    {

        prefs = Gdx.app.getPreferences("carColor");

        car = new Texture("character1.png");
        carMagenta = new Texture("charMagenta.png");
        carPurple = new Texture("charPurple.png");

        car.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        carMagenta.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        carPurple.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        position = new Vector3(x, y, 0);
        bounds = new Rectangle(x, y, car.getWidth(), car.getHeight());

    }

    public void update(float dt)
    {
        if (dt > 0)
        {
            //position.add(0,15,0);
            bounds.setPosition(position.x, position.y);
        }

    }

    public Texture getcharacter()
    {
        carNum = prefs.getInteger("carColor", 0);

        if (carNum == 1)
            return carMagenta;
        else if (carNum == 2)
            return carPurple;
        else
            return car;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void dispose()
    {
        car.dispose();
    }
}
