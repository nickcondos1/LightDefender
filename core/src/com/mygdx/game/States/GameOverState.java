package com.mygdx.game.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.AdHandler;
import com.mygdx.game.Sprite.Character;
import com.mygdx.game.LightDefender;



public class GameOverState extends State
{
    private Texture gameover;
    private Texture playagain;
    private Texture backbutton;
    private Texture backword;
    private Texture playword;
    private Texture gameClassic;
    private Texture gameSpeed;
    private int counter;
    private BitmapFont font;
    private String score;
    private Character character;
    private Rectangle back;
    private Rectangle play;
    private boolean gameMode;

    private int highscore = 0;
    private Preferences prefs;
    private Preferences prefs2;


    public GameOverState(GameStateManager gsm, int number, boolean mode)
    {
        super(gsm);
        prefs = Gdx.app.getPreferences("highscore");
        prefs2 = Gdx.app.getPreferences("speedhighscore");
        gameMode = mode;

        character = new Character(LightDefender.WIDTH / 2 - 45, 100);
        gameover = new Texture("gameoverHD.png");
        playagain = new Texture("playagainbutton.png");
        playword = new Texture("playagain.png");
        backbutton = new Texture("backbutton.png");
        backword = new Texture("backword.png");
        gameClassic = new Texture("classictext.png");
        gameSpeed = new Texture("speedruntext.png");

        back = new Rectangle(10, 10, backbutton.getWidth(), backbutton.getHeight());
        play = new Rectangle(LightDefender.WIDTH - playagain.getWidth() - 10, 10, playagain.getWidth(), playagain.getHeight());

        gameover.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playagain.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backbutton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playword.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backword.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gameClassic.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gameSpeed.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        counter = number;
        font = new BitmapFont(Gdx.files.internal("LithosPro.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        score = counter + " ";

        cam.setToOrtho(false, LightDefender.WIDTH, LightDefender.HEIGHT);

        highscore = this.GetHighScore();

        if (counter > highscore)
            this.CheckScore();

        //LightDefender.handler.showAds(true);
        LightDefender.toggle = true;



    }

    public void CheckScore()
    {
        highscore = counter;

        if (gameMode == true) {
            prefs.putInteger("highscore", highscore);
            prefs.flush();
        }
        else {
            prefs2.putInteger("speedhighscore", highscore);
            prefs2.flush();
        }
    }

    public int GetHighScore()
    {
        int score;

        if (gameMode == true)
            score = prefs.getInteger("highscore");
        else
            score = prefs2.getInteger("speedhighscore");

        return score;
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

            if (character.getBounds().overlaps(back)) {
                gsm.set(new SelectionState(gsm));
            }
            if (character.getBounds().overlaps(play)) {
                if (gameMode == true)
                    gsm.set(new PlayState(gsm));
                else
                    gsm.set(new SpeedState(gsm));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb)
    {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(gameover,cam.position.x - (cam.viewportWidth / 2),cam.position.y - (cam.viewportHeight / 2));
        sb.draw(playagain, LightDefender.WIDTH - playagain.getWidth() - 15, 10);
        sb.draw(backbutton, 15, 10);
        sb.draw(playword, LightDefender.WIDTH - playagain.getWidth() - 60, 210);
        sb.draw(backword, 5, 215);
        sb.draw(character.getcharacter(), character.getPosition().x, character.getPosition().y);

        if (gameMode == true)
            sb.draw(gameClassic, LightDefender.WIDTH / 2 - gameClassic.getWidth() / 2, LightDefender.HEIGHT - 230);
        else
            sb.draw(gameSpeed, LightDefender.WIDTH / 2 - gameSpeed.getWidth() / 2, LightDefender.HEIGHT - 300);

        font.draw(sb, score, LightDefender.WIDTH / 2 + 100, 940);
        font.draw(sb, highscore + " ", LightDefender.WIDTH / 2 + 350, 750);

        sb.end();

    }

    @Override
    public void dispose()
    {
        gameover.dispose();
        playagain.dispose();
        backbutton.dispose();
        character.dispose();
        playword.dispose();
        backword.dispose();
        gameClassic.dispose();
        gameSpeed.dispose();
    }


}
