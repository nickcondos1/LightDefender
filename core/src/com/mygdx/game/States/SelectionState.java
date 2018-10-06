package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.LightDefender;
import com.mygdx.game.Sprite.Character;

public class SelectionState extends State
{
    private Texture selectionscreen;
    private Texture squareOne;
    private Texture squareTwo;
    private Texture charSel;
    private Texture charword;
    private Texture selectword;
    private Texture choosegame;
    private Rectangle one;
    private Rectangle two;
    private Rectangle cSel;
    private Vector3 pos;


    private Character character;

    public SelectionState(GameStateManager gsm)
    {
        super(gsm);

        selectionscreen = new Texture("selectionmenu.png");
        squareOne = new Texture("classic.png");
        squareTwo = new Texture("speedrun.png");
        charSel = new Texture("tocharmenu.png");
        charword = new Texture("characterword.png");
        selectword = new Texture("selectionword.png");
        choosegame = new Texture("choosegame.png");

        selectionscreen.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        squareOne.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        squareTwo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        charSel.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        charword.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        selectword.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        choosegame.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        one = new Rectangle(LightDefender.WIDTH / 2 - squareOne.getWidth() / 2, LightDefender.HEIGHT / 3 - 400, squareOne.getWidth(), squareOne.getHeight());
        two = new Rectangle(LightDefender.WIDTH / 2 - squareTwo.getWidth() / 2, (LightDefender.HEIGHT / 3 * 2) - 400, squareTwo.getWidth(), squareTwo.getHeight());
        cSel = new Rectangle(LightDefender.WIDTH / 2 - charSel.getWidth() / 2, LightDefender.HEIGHT - charSel.getHeight() - 10, charSel.getWidth(), charSel.getHeight());

        character = new Character( 190, 10);
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
        if (dt > 0) {

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

            if (character.getBounds().overlaps(one))
            {
                gsm.set(new ClassicInstructions(gsm));
            }
            if (character.getBounds().overlaps(two))
            {
                gsm.set(new SpeedInstructions(gsm));
            }
            if (character.getBounds().overlaps(cSel))
            {
                gsm.set(new CharacterState(gsm));
            }

        }
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(selectionscreen,0,0);
        sb.draw(squareOne, LightDefender.WIDTH / 2 - squareOne.getWidth() / 2, LightDefender.HEIGHT / 3 - 400);
        sb.draw(squareTwo, LightDefender.WIDTH / 2 - squareTwo.getWidth() / 2, (LightDefender.HEIGHT / 3 * 2) - 400);
        sb.draw(charSel, LightDefender.WIDTH / 2 - charSel.getWidth() / 2, LightDefender.HEIGHT - charSel.getHeight() - 10);
        sb.draw(charword, 25, LightDefender.HEIGHT - charSel.getHeight() - 25);
        sb.draw(selectword, 855, 2310);
        sb.draw(choosegame, LightDefender.WIDTH / 2 - choosegame.getWidth() / 2, 1850);
        sb.draw(character.getcharacter(), character.getPosition().x, character.getPosition().y);

        sb.end();
    }

    @Override
    public void dispose()
    {
        selectionscreen.dispose();
        character.dispose();
        squareTwo.dispose();
        squareOne.dispose();
        charSel.dispose();
        charword.dispose();
        selectword.dispose();
        choosegame.dispose();
    }
}
