package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.LightDefender;
import com.mygdx.game.Sprite.BlueBlock;
import com.mygdx.game.Sprite.Character;
import com.mygdx.game.Sprite.Light;
import com.mygdx.game.Sprite.OrangeBlock;
import com.mygdx.game.Sprite.Turquoise;

public class SpeedState extends State
{
    private static final int TURQUOISE_COUNT = 3;

    private Character character;
    private Texture ground;
    private Texture groundchange;
    private Vector2 groundPos1, groundPos2;
    private BitmapFont font;
    private String score;
    private int counter;

    private Array<BlueBlock> blues;
    private Array<Turquoise> turquoises;
    private Array<OrangeBlock> oranges;
    private Array<Light> lights;

    public SpeedState(GameStateManager gsm)
    {
        super(gsm);
        cam.setToOrtho(false, LightDefender.WIDTH, LightDefender.HEIGHT);

        ground = new Texture ("backgroundHDlighter.png");
        ground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        character = new Character( LightDefender.WIDTH / 2 - 45, 300); //200
        groundPos1 = new Vector2(0, cam.position.y - cam.viewportHeight / 2);
        groundPos2 = new Vector2(0, (cam.position.y - cam.viewportHeight / 2) + ground.getHeight());
        counter = 0;
        font = new BitmapFont(Gdx.files.internal("LithosPro.fnt"));
        score = counter + " ";

        blues = new Array<BlueBlock>();
        turquoises = new Array<Turquoise>();
        oranges = new Array<OrangeBlock>();
        lights = new Array<Light>();

        lights.add(new Light(1300)); //800

        oranges.add(new OrangeBlock(25, cam.position.y - cam.viewportHeight / 2 + 1500)); //200
        oranges.add(new OrangeBlock(LightDefender.WIDTH - 150, cam.position.y + cam.viewportHeight / 2 + 400)); // -100
        oranges.add(new OrangeBlock(LightDefender.WIDTH / 2 - oranges.get(0).getOrangeBlock().getWidth() / 2 + 3, cam.position.y + cam.viewportHeight / 2 - 0)); // - 1200

        blues.add(new BlueBlock(LightDefender.WIDTH + 60, (cam.position.y + cam.viewportHeight / 2) )); //-500
        blues.add(new BlueBlock(-250, (cam.position.y + cam.viewportHeight / 2) )); //-500
        blues.add(new BlueBlock(LightDefender.WIDTH + 60, (cam.position.y + cam.viewportHeight / 2) ));//-500

        for (int i = 0; i < TURQUOISE_COUNT; i++)
        {
            turquoises.add(new Turquoise(cam.position.y + cam.viewportHeight / 2 + 500)); // +0

            if (i > 0)
            {
                if (turquoises.get(i).getBounds().overlaps(turquoises.get(i - 1).getBounds()))
                    turquoises.get(i).setPosition(150);
            }
        }

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
                    character.setPosition(character.getPosition().add(30, 0, 0));
                }
            }
            else {
                if (character.getPosition().x <= 0) {
                    character.setPosition(character.getPosition().add(0, 0, 0));
                }
                else {
                    character.setPosition(character.getPosition().add(-30, 0, 0));
                }
            }

        }
    }

    @Override
    public void update(float dt)
    {
        handleInput();
        updateGround();
        reset();
        character.update(dt);
        character.setPosition(character.getPosition().add(0,40,0));

        cam.position.y = character.getPosition().y + 1000; //1150

        for (BlueBlock i : blues)
        {
            if (cam.position.y - (cam.viewportHeight) > i.getPosition().y + i.getBlueBlock().getHeight())
                i.reposition();

            if (i.collides(character.getBounds()))
                gsm.set(new GameOverState(gsm, counter, false));

            i.update(dt);
        }

        for (int i = 0; i < turquoises.size; i++)
        {
            if (cam.position.y - (cam.viewportHeight) > turquoises.get(i).getPosition().y + turquoises.get(i).getTurquoise().getHeight())
                turquoises.get(i).reposition(cam.position.y + cam.viewportHeight / 2);

            if (turquoises.get(i).collides(character.getBounds()))
                gsm.set(new GameOverState(gsm, counter, false));

            turquoises.get(i).update(dt);
        }

        for (OrangeBlock a : oranges)
        {
            if (cam.position.y - (cam.viewportHeight) > a.getPosition().y + a.getOrangeBlock().getHeight())
                a.reposition();

            if (a.collides(character.getBounds()))
                gsm.set(new GameOverState(gsm, counter, false));

            a.update(dt);
        }


        for (Light l : lights)
        {
            if (cam.position.y - (cam.viewportHeight) > l.getPosition().y + l.getLight().getHeight())
                l.reposition();

            if (l.collides(character.getBounds())) {
                lights.removeIndex(0);
                counter++;
                score = counter + " ";
                lights.add(new Light(3000 + character.getPosition().y));
            }

            l.update(dt);
        }

        if (counter > 15 && counter < 25) {
            if (blues.size == 3 && blues.get(2).getPosition().x == 0)
                blues.removeIndex(2);
        }

        cam.update();


    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        for (BlueBlock i : blues)
        {
            sb.draw(i.getBlueBlock(), i.getPosition().x, i.getPosition().y);
        }

        for (Turquoise t : turquoises)
        {
            sb.draw(t.getTurquoise(), t.getPosition().x, t.getPosition().y);
        }

        for (OrangeBlock a : oranges)
        {
            sb.draw(a.getOrangeBlock(), a.getPosition().x, a.getPosition().y);
        }

        for (Light l : lights)
        {
            sb.draw(l.getLight(), l.getPosition().x, l.getPosition().y);
        }

        sb.draw(character.getcharacter(), character.getPosition().x, character.getPosition().y);
        font.draw(sb, score, 50, (cam.position.y + cam.viewportHeight / 2) - 50);
        sb.end();
    }

    private void reset()
    {
        for (int i = 0; i < blues.size; i++)
        {
            if (blues.get(i).getPosition().y < cam.position.y - (cam.viewportHeight / 2) - 250)
            {
                blues.get(i).reposition();
            }
        }
    }

    private void updateGround()
    {
        if (cam.position.y - (cam.viewportHeight / 2) > groundPos1.y + ground.getHeight()) {
            groundPos1.add(0, ground.getHeight() * 2);

            counter ++;
            score = counter + " ";
        }
        if (cam.position.y - (cam.viewportHeight / 2) > groundPos2.y + ground.getHeight()) {
            groundPos2.add(0, ground.getHeight() * 2);

            counter ++;
            score = counter + " ";
        }
    }

    @Override
    public void dispose()
    {
        for (int i = 0; i < blues.size; i++)
        {
            blues.get(i).dispose();
        }

        for (int i = 0; i < turquoises.size; i++)
        {
            turquoises.get(i).dispose();
        }

        for (int i = 0; i < oranges.size; i++)
        {
            oranges.get(i).dispose();
        }

        character.dispose();
        ground.dispose();
    }

}
