package com.mygdx.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State
{
    protected OrthographicCamera cam;
    protected GameStateManager gsm; //Can put a state on top of a state. ie, pause ontop of gamestate

    protected State(GameStateManager gsm)
    {
        this.gsm = gsm;
        cam = new OrthographicCamera();

    }

    protected abstract void handleInput(); //Most of the user "input" will be dealt with here
    public abstract void update(float dt); //Deltatime, difference between one framerate to another
    public abstract void render(SpriteBatch sb); //Renders all our graphics to screen
    public abstract void dispose(); //Clean up textures here
}
