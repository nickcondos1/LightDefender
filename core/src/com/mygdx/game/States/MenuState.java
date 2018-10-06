package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LightDefender;

public class MenuState extends State
{
    private Texture mainMenu;

    public MenuState(GameStateManager gsm)
    {
        super(gsm);
        mainMenu = new Texture("mainmenu1.png");
        mainMenu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        cam.setToOrtho(false, LightDefender.WIDTH, LightDefender.HEIGHT);

        LightDefender.toggle = false;

    }

    @Override
    protected void handleInput()
    {
        if(Gdx.input.justTouched())
        {
            gsm.set(new InstructionState(gsm));
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
        sb.draw(mainMenu, 0, 0);
        sb.end();
    }

    @Override
    public void dispose()
    {
        mainMenu.dispose();
    }
}
