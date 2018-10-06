package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.LightDefender;
import com.mygdx.game.Sprite.Character;

public class CharacterState extends State
{
    private Texture background;
    private Texture magenta;
    private Texture purple;
    private Texture gray;
    private Texture backword;
    private Texture backbtn;

    private Rectangle onebound;
    private Rectangle twobound;
    private Rectangle threebound;
    private Rectangle backbound;

    private Character character;
    private Vector3 pos;
    private Preferences prefs;

    public CharacterState(GameStateManager gsm)
    {
        super(gsm);
        prefs = Gdx.app.getPreferences("carColor");


        background = new Texture("selectionmenu.png");
        magenta = new Texture("magenta.png");
        purple = new Texture("purple.png");
        gray = new Texture("gray.png");
        backbtn = new Texture("backbutton.png");
        backword = new Texture("backword.png");

        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        magenta.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        purple.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gray.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backbtn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backword.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        onebound = new Rectangle(LightDefender.WIDTH / 3 - magenta.getWidth() / 2, LightDefender.HEIGHT / 2 - 300, magenta.getWidth(), magenta.getHeight());
        twobound = new Rectangle(LightDefender.WIDTH / 3 * 2 - purple.getWidth() / 2, LightDefender.HEIGHT / 2 - 300, purple.getWidth(), purple.getHeight());
        threebound = new Rectangle(LightDefender.WIDTH / 2 - gray.getWidth() / 2, 1400, gray.getWidth(), gray.getHeight());
        backbound = new Rectangle(LightDefender.WIDTH / 2 - backbtn.getWidth() / 2, LightDefender.HEIGHT - backbtn.getHeight() - 10, backbtn.getWidth(), backbtn.getHeight());

        character = new Character(50, 10);
        pos = new Vector3(character.getPosition().x, character.getPosition().y, 0);

        cam.setToOrtho(false, LightDefender.WIDTH, LightDefender.HEIGHT);
        LightDefender.toggle = false;
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.isTouched()) {

            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                if (character.getPosition().x >= LightDefender.WIDTH - character.getcharacter().getWidth()) {
                    character.setPosition(character.getPosition().add(0, 0, 0));
                }
                else {
                    character.setPosition(character.getPosition().add(20, 0, 0));
                }
            }
            else {
                if (character.getPosition().x <= 0) {
                    character.setPosition(character.getPosition().add(0, 0, 0));
                }
                else {
                    character.setPosition(character.getPosition().add(-20, 0, 0));
                }
            }

        }
    }

    @Override
    public void update(float dt)
    {
        if (dt > 0)
        {
            handleInput();
            character.update(dt);
            character.setPosition(character.getPosition().add(0, 12, 0));

            if (character.getPosition().y > LightDefender.HEIGHT)
            {
                pos.x = character.getPosition().x;
                pos.y = 10;
                pos.z = 0;
                character.setPosition(pos);
            }

            if (character.getBounds().overlaps(onebound))
            {
                prefs.putInteger("carColor", 1);
                prefs.flush();
            }
            if (character.getBounds().overlaps(twobound))
            {
                prefs.putInteger("carColor", 2);
                prefs.flush();
            }
            if (character.getBounds().overlaps(threebound))
            {
                prefs.putInteger("carColor", 0);
                prefs.flush();
            }
            if (character.getBounds().overlaps(backbound))
            {
                gsm.set(new SelectionState(gsm));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(cam.combined);

        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(character.getcharacter(), character.getPosition().x, character.getPosition().y);
        sb.draw(magenta, LightDefender.WIDTH / 3 - magenta.getWidth() / 2, LightDefender.HEIGHT / 2 - 300);
        sb.draw(purple, LightDefender.WIDTH / 3 * 2 - purple.getWidth() / 2, LightDefender.HEIGHT / 2 - 300);
        sb.draw(gray, LightDefender.WIDTH / 2 - gray.getWidth() / 2, 1400);
        sb.draw(backbtn, LightDefender.WIDTH / 2 - backbtn.getWidth() / 2, LightDefender.HEIGHT - backbtn.getHeight() - 10);
        sb.draw(backword, LightDefender.WIDTH / 2 - backword.getWidth() / 2, 2125);
        sb.end();
    }

    @Override
    public void dispose()
    {
        magenta.dispose();
        purple.dispose();
        backbtn.dispose();
        background.dispose();
        backword.dispose();
        gray.dispose();
    }
}
