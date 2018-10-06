package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LightDefender;

public class SpeedInstructions extends State
{
    private Texture instructions;

    public SpeedInstructions(GameStateManager gsm)
    {
        super(gsm);
        instructions = new Texture("speedinst.png");
        instructions.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        cam.setToOrtho(false, LightDefender.WIDTH, LightDefender.HEIGHT);

        LightDefender.toggle = false;

    }

    @Override
    protected void handleInput()
    {
        if(Gdx.input.justTouched())
        {
            gsm.set(new SpeedState(gsm));
            //gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt)
    {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(instructions, 0, 0);
        sb.end();
    }

    @Override
    public void dispose()
    {
        instructions.dispose();
    }
}
